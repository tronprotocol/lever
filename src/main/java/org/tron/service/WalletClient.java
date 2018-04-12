package org.tron.service;

import static org.tron.core.config.Parameter.CommonConstant.TARGET_GRPC_ADDRESS;

import com.google.protobuf.ByteString;
import com.typesafe.config.Config;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.tron.api.GrpcAPI.AccountList;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.core.config.Configuration;
import org.tron.protos.Contract;
import org.tron.protos.Protocol.Transaction;

@Slf4j
public class WalletClient {

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

  public void init() {
    if (!config.hasPath(TARGET_GRPC_ADDRESS)) {
      logger.error("no target: {} = ip:host", TARGET_GRPC_ADDRESS);
      return;
    }
    String target = config.getString(TARGET_GRPC_ADDRESS);
    logger.info("target: {}" + target);
    rpcCli = new GrpcClient(target);
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
