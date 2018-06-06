package org.tron.service;

import static org.tron.core.config.Parameter.CommonConstant.TARGET_GRPC_ADDRESS;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.typesafe.config.Config;
import java.util.List;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.core.config.Configuration;
import org.tron.protos.Contract;
import org.tron.protos.Contract.FreezeBalanceContract;
import org.tron.protos.Contract.TransferContract;
import org.tron.protos.Contract.VoteWitnessContract;
import org.tron.protos.Protocol.Account;
import org.tron.protos.Protocol.Transaction;

@Slf4j
@Component
public class WalletClient {

  @Getter
  private GrpcClient rpcCli;
  private ECKey ecKey;

  private Config config = Configuration.getByPath("config.conf");

  public WalletClient() {

  }

  public WalletClient(String priKey) {
    ECKey temKey = null;
    try {
      temKey = ECKey.fromPrivate(ByteArray.fromHexString(priKey));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    this.ecKey = temKey;
  }

  public static Contract.TransferContract createTransferContract(byte[] to, byte[] owner,
      long amount) {
    Contract.TransferContract.Builder builder = Contract.TransferContract.newBuilder();
    ByteString bsTo = ByteString.copyFrom(to);
    ByteString bsOwner = ByteString.copyFrom(owner);
    builder.setToAddress(bsTo);
    builder.setOwnerAddress(bsOwner);
    builder.setAmount(amount);

    return builder.build();
  }

  public static Transaction createTransaction(TransferContract contract) {
    Transaction.Builder transactionBuilder = Transaction.newBuilder();
    Transaction.Contract.Builder contractBuilder = Transaction.Contract.newBuilder();
    try {
      Any anyTo = Any.pack(contract);
      contractBuilder.setParameter(anyTo);
    } catch (Exception e) {
      return null;
    }
    contractBuilder.setType(Transaction.Contract.ContractType.TransferContract);
    transactionBuilder.getRawDataBuilder().addContract(contractBuilder);
    Transaction transaction = transactionBuilder.build();

    return transaction;
  }

  @Autowired
  public void init(@Value("0") int index) {
    if (!config.hasPath(TARGET_GRPC_ADDRESS)) {
      logger.error("no target: {} = [ip:host]", TARGET_GRPC_ADDRESS);
      return;
    }
    List<String> target = config.getStringList(TARGET_GRPC_ADDRESS);
    logger.info("target: {}" + target);
    rpcCli = new GrpcClient(target.get(index % target.size()));
  }

  public void init(String address) {
    rpcCli = new GrpcClient(address);
  }

  public void shutdown() {
    if (rpcCli != null) {
      try {
        rpcCli.shutdown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public boolean sendCoin(byte[] to, long amount) {
    byte[] owner = getAddress();

    Contract.TransferContract contract = createTransferContract(to, owner, amount);
    Transaction transaction = createTransaction(contract);
    if (transaction == null || transaction.getRawData().getContractCount() == 0) {
      return false;
    }

    transaction = signTransaction(transaction);
    return rpcCli.broadcastTransaction(transaction);
  }

  public Transaction createTransaction(byte[] to, long amount, String privateKey) {
    ECKey ecKey = ECKey.fromPrivate(ByteArray.fromHexString(privateKey));

    byte[] owner = ecKey.getAddress();

    Contract.TransferContract contract = createTransferContract(to, owner, amount);
    Transaction transaction = rpcCli.createTransaction(contract);
    if (transaction == null || transaction.getRawData().getContractCount() == 0) {
      return null;
    }

    transaction = signTransaction(transaction, ecKey);
    return transaction;
  }

  public boolean broadcastTransaction(Transaction transaction) {
    return rpcCli.broadcastTransaction(transaction);
  }

  public Account getAccount(Account account) {
    return rpcCli.getAccount(account);
  }

  public Transaction signTransaction(Transaction transaction) {
    if (this.ecKey == null || this.ecKey.getPrivKey() == null) {
      return null;
    }
    transaction = TransactionUtils.setTimestamp(transaction);
    return TransactionUtils.sign(transaction, this.ecKey);
  }

  public synchronized Transaction signTransaction(Transaction transaction, ECKey ecKey) {
    if (ecKey == null || ecKey.getPrivKey() == null) {
      return null;
    }

    transaction = TransactionUtils.setTimestamp(transaction);
    return TransactionUtils.sign(transaction, ecKey);
  }

  public byte[] getAddress() {
    return ecKey.getAddress();
  }

  public boolean freezeBalance(String privateKey, long frozen_balance, long frozen_duration) {
    ECKey ecKey = ECKey.fromPrivate(ByteArray.fromHexString(privateKey));

    Contract.FreezeBalanceContract contract = createFreezeBalanceContract(ecKey.getAddress(),
        frozen_balance,
        frozen_duration);

    Transaction transaction = rpcCli.createTransaction(contract);

    if (transaction == null || transaction.getRawData().getContractCount() == 0) {
      return false;
    }

    transaction = signTransaction(transaction, ecKey);
    return rpcCli.broadcastTransaction(transaction);
  }

  private FreezeBalanceContract createFreezeBalanceContract(byte[] address, long frozen_balance,
      long frozen_duration) {
    Contract.FreezeBalanceContract.Builder builder = Contract.FreezeBalanceContract.newBuilder();
    ByteString byteAddress = ByteString.copyFrom(address);

    builder.setOwnerAddress(byteAddress).setFrozenBalance(frozen_balance)
        .setFrozenDuration(frozen_duration);

    return builder.build();
  }

  public Transaction voteWitnessTransaction(VoteWitnessContract contract) {
    return rpcCli.voteWitnessAccount(contract);
  }
}
