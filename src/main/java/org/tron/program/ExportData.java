package org.tron.program;

import static org.tron.service.WalletClient.createTransaction;
import static org.tron.service.WalletClient.createTransferContract;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.tron.Validator.LongValidator;
import org.tron.Validator.StringValidator;
import org.tron.capsule.BlockCapsule;
import org.tron.capsule.utils.BlockUtil;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.CsvUtils;
import org.tron.core.config.Account;
import org.tron.core.config.Configuration;
import org.tron.core.config.GenesisBlock;
import org.tron.core.config.Witness;
import org.tron.protos.Contract;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletClient;

public class ExportData {

  private static WalletClient walletClient;

  //Example:
  //--toaddress toaddress.csv --amount 1 --output trxsdata.csv --count 10000 --privatekey privatekey.csv
  public static void main(String[] args) throws IOException {
    Args argsObj = new Args();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    walletClient = new WalletClient();
    walletClient.init(0);

    byte[] refBlockHash = getRefBlockHash(argsObj.getGenesisConfig());

    if (null == refBlockHash) {
      return;
    }

    System.out.println("xxxd " + ByteArray.toHexString(refBlockHash));

    // 读取to address
    List<String> toAddressList = getStrings(argsObj.getToAddress());
    List<byte[]> toAddressByteList = new ArrayList<>();
    int addressSize = toAddressList.size();
    if (addressSize == 0) {
      System.out.println("address is empty");
      return;
    }

    // 读取 private key
    List<String> privateKeyList = getStrings(argsObj.getPrivateKey());
    int privateKeySize = privateKeyList.size();
    if (privateKeySize == 0) {
      System.out.println("private key is empty");
      return;
    }

    File f = new File(argsObj.getOutput());
    FileOutputStream fos = new FileOutputStream(f);

    for (String toAddress : toAddressList) {
      byte[] addressBytes = Base58.decodeFromBase58Check(toAddress);
      toAddressByteList.add(addressBytes);
    }
    long amount = argsObj.getAmount();

    ConcurrentLinkedQueue<Transaction> transactions = new ConcurrentLinkedQueue<>();
    AtomicInteger counter = new AtomicInteger(0);

    int availProcessors = Runtime.getRuntime().availableProcessors();
    List<Integer> processors = new ArrayList<>();
    for (int i = 0; i < availProcessors; i++) {
      processors.add(i);
    }

    processors.stream().parallel().forEach(item -> {
      for (int i = 0; i < argsObj.getCount() / processors.size(); i++) {
        int c = counter.incrementAndGet();
        Transaction transaction = generateTransaction(toAddressByteList.get(c % addressSize),
            amount, privateKeyList.get(c % privateKeySize), refBlockHash);
        transactions.add(transaction);
        if ((c + 1) % 1000 == 0) {
          System.out.println("create transaction current: " + (c + 1));
        }
      }
    });
    counter.getAndSet(0);
    for (Transaction transaction : transactions) {
      transaction.writeDelimitedTo(fos);
      long c = counter.incrementAndGet();
      if ((c + 1) % 1000 == 0) {
        System.out.println("write file current: " + (c + 1));
      }
    }
    fos.flush();
    fos.close();
  }

  private static Transaction generateTransaction(byte[] to, long amount, String privateKey, byte[] refBlockHash) {
    ECKey ecKey = ECKey.fromPrivate(ByteArray.fromHexString(privateKey));

    byte[] owner = ecKey.getAddress();

    Contract.TransferContract contract = createTransferContract(to, owner, amount);
    Transaction transaction = createTransaction(contract);
    if (transaction == null || transaction.getRawData().getContractCount() == 0) {
      return null;
    }

    transaction = walletClient.setReferesnce(transaction, 0, refBlockHash);

    transaction = walletClient.signTransaction(transaction, ecKey);

    return transaction;
  }

  private static List<String> getStrings(String filePath) {
    List<CSVRecord> read = CsvUtils.read(new File(filePath));
    List<String> stringList = new ArrayList();

    read.forEach(l -> {
      stringList.add(l.get(0));
    });

    return stringList;
  }

  private static byte[] getRefBlockHash(String genesisConfig) {
    Config config = Configuration.getByPath(genesisConfig);

    GenesisBlock genesisBlock = new GenesisBlock();
    if (config.hasPath("genesis.block")) {

      genesisBlock.setTimestamp(config.getString("genesis.block.timestamp"));
      genesisBlock.setParentHash(config.getString("genesis.block.parentHash"));

      if (config.hasPath("genesis.block.assets")) {
        genesisBlock.setAssets(getAccountsFromConfig(config));
      }
      if (config.hasPath("genesis.block.witnesses")) {
        genesisBlock.setWitnesses(getWitnessesFromConfig(config));
      }
    } else {
      System.out.println("Must have genesis block config");
      return null;
    }

    BlockCapsule blockCapsule = BlockUtil.newGenesisBlockCapsule(genesisBlock);
    byte[] refBlockHash = blockCapsule.getBlockId().getBytes();

    return refBlockHash;
  }

  private static List<Account> getAccountsFromConfig(final com.typesafe.config.Config config) {
    return config.getObjectList("genesis.block.assets").stream()
        .map(ExportData::createAccount)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private static Account createAccount(final ConfigObject asset) {
    final Account account = new Account();
    account.setAccountName(asset.get("accountName").unwrapped().toString());
    account.setAccountType(asset.get("accountType").unwrapped().toString());
    account.setAddress(Base58.decodeFromBase58Check(asset.get("address").unwrapped().toString()));
    account.setBalance(asset.get("balance").unwrapped().toString());
    return account;
  }

  private static List<Witness> getWitnessesFromConfig(final com.typesafe.config.Config config) {
    return config.getObjectList("genesis.block.witnesses").stream()
        .map(ExportData::createWitness)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private static Witness createWitness(final ConfigObject witnessAccount) {
    final Witness witness = new Witness();
    witness.setAddress(Base58.decodeFromBase58Check(witnessAccount.get("address").unwrapped().toString()));
    witness.setUrl(witnessAccount.get("url").unwrapped().toString());
    witness.setVoteCount(witnessAccount.toConfig().getLong("voteCount"));
    return witness;
  }
}

class Args {

  @Getter
  @Parameter(names = {
      "--toaddress"}, description = "To address file", required = true, validateWith = StringValidator.class)
  private String toAddress;

  @Getter
  @Parameter(names = {
      "--privatekey"}, description = "Private key file", required = true, validateWith = StringValidator.class)
  private String privateKey;

  @Getter
  @Parameter(names = {
      "--amount"}, description = "Amount", required = true, validateWith = LongValidator.class)
  private long amount;

  @Getter
  @Parameter(names = {
      "--count"}, description = "Count", required = true, validateWith = LongValidator.class)
  private long count;

  @Getter
  @Parameter(names = {
      "--output"}, description = "Save data file", required = true, validateWith = StringValidator.class)
  private String output;

  @Getter
  @Parameter(names = {
      "--genesisconfig"}, description = "Genesis block config file", required = true, validateWith = StringValidator.class)
  private String genesisConfig;
}