package org.tron.service;

import com.google.protobuf.ByteString;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.protos.Contract;
import org.tron.protos.Contract.FreezeBalanceContract;
import org.tron.protos.Protocol.Account;
import org.tron.protos.Protocol.Transaction;

@Slf4j
@Component
public class WalletClient {

  @Getter
  private GrpcClient rpcCli;

  public WalletClient() {

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

  public boolean broadcastTransaction(Transaction transaction) {
    return rpcCli.broadcastTransaction(transaction);
  }

  public Account getAccount(Account account) {
    return rpcCli.getAccount(account);
  }

  public static synchronized Transaction signTransaction(Transaction transaction, ECKey ecKey) {
    if (ecKey == null || ecKey.getPrivKey() == null) {
      return null;
    }

    transaction = TransactionUtils.setTimestamp(transaction);
    return TransactionUtils.sign(transaction, ecKey);
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
}
