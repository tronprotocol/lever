package org.tron.service;

import com.google.protobuf.ByteString;
import java.math.BigInteger;
import java.util.Optional;
import org.tron.api.GrpcAPI.AccountList;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.protos.Contract;
import org.tron.protos.Protocol.Transaction;

public class WalletClient {

  private GrpcClient rpcCli;
  private ECKey ecKey;

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

  public void init() {
    rpcCli = new GrpcClient("47.93.33.201:50051");
  }

  public Optional<AccountList> listAccounts() {
    return rpcCli.listAccounts();
  }

  public boolean sendCoin(byte[] to, long amount) {
    byte[] owner = getAddress();

    Contract.TransferContract contract = createTransferContract(to, owner, amount);
    Transaction transaction = rpcCli.createTransaction(contract);
    if (transaction == null || transaction.getRawData().getContractCount() == 0) {
      return false;
    }
    System.out.println(2);
    transaction = signTransaction(transaction);
    System.out.println(3);
    return rpcCli.broadcastTransaction(transaction);
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

  private Transaction signTransaction(Transaction transaction) {
    if (this.ecKey == null || this.ecKey.getPrivKey() == null) {
      return null;
    }
    transaction = TransactionUtils.setTimestamp(transaction);
    return TransactionUtils.sign(transaction, this.ecKey);
  }

  public byte[] getAddress() {
    return ecKey.getAddress();
  }
}
