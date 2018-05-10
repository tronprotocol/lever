package org.tron.program;

import static org.tron.service.WalletClient.createTransaction;
import static org.tron.service.WalletClient.createTransferContract;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.tron.Validator.LongValidator;
import org.tron.Validator.StringValidator;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.CsvUtils;
import org.tron.protos.Contract;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletClient;

public class ExportData {

  private static WalletClient walletClient;

  //Example:
  //--toaddress toaddress.csv --amount 1 --output trxsdata.csv --count 10000 --privatekey privatekey.csv
  public static void main(String[] args) throws IOException {
    Args args1 = new Args();
    JCommander.newBuilder().addObject(args1).build().parse(args);

    walletClient = new WalletClient();
    walletClient.init();

    // 读取to address
    List<String> toAddressList = getStrings(args1.getToAddress());
    List<byte[]> toAddressByteList = new ArrayList<>();
    int addressSize = toAddressList.size();
    if (addressSize == 0) {
      System.out.println("address is empty");
      return;
    }

    // 读取 private key
    List<String> privateKeyList = getStrings(args1.getPrivateKey());
    int privateKeySize = privateKeyList.size();
    if (privateKeySize == 0) {
      System.out.println("private key is empty");
      return;
    }

    File f = new File(args1.getOutput());
    FileOutputStream fos = new FileOutputStream(f);

    for (String toAddress : toAddressList) {
      byte[] addressBytes = Base58.decodeFromBase58Check(toAddress);
      toAddressByteList.add(addressBytes);
    }
    long amount = args1.getAmount();

    ConcurrentLinkedQueue<Transaction> transactions = new ConcurrentLinkedQueue<>();
    AtomicInteger counter = new AtomicInteger(0);

    int availProcessors = Runtime.getRuntime().availableProcessors();
    List<Integer> processors = new ArrayList<>();
    for (int i = 0; i < availProcessors; i++) {
      processors.add(i);
    }

    processors.stream().parallel().forEach(item -> {
      for (int i = 0; i < args1.getCount() / processors.size(); i++) {
        int c = counter.incrementAndGet();
        Transaction transaction = generateTransaction(toAddressByteList.get(c % addressSize),
            amount, privateKeyList.get(c % privateKeySize));
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
//    for (int i = 0; i < args1.getCount(); ++i) {
//      String address = toAddress.get(i % size);
//      byte[] addressBytes = Base58.decodeFromBase58Check(address);
//      long amount = args1.getAmount();
//      if (addressBytes != null) {
//        Transaction transaction = generateTransaction(addressBytes, amount);
//        transaction.writeDelimitedTo(fos);
//        if (i % 1000 == 0) {
//          System.out.println("current: " + (i + 1));
//        }
//      }
//    }
  }

  private static Transaction generateTransaction(byte[] to, long amount, String privateKey) {
    ECKey ecKey = ECKey.fromPrivate(ByteArray.fromHexString(privateKey));

    byte[] owner = ecKey.getAddress();

    Contract.TransferContract contract = createTransferContract(to, owner, amount);
    Transaction transaction = createTransaction(contract);
    if (transaction == null || transaction.getRawData().getContractCount() == 0) {
      return null;
    }

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
}