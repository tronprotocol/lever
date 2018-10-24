package org.tron.service;

import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.tron.api.GrpcAPI;
import org.tron.api.GrpcAPI.BytesMessage;
import org.tron.api.GrpcAPI.EmptyMessage;
import org.tron.api.GrpcAPI.NumberMessage;
import org.tron.api.GrpcAPI.Return.response_code;
import org.tron.api.GrpcAPI.TransactionExtention;
import org.tron.api.WalletGrpc;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Sha256Hash;
import org.tron.protos.Contract;
import org.tron.protos.Contract.AssetIssueContract;
import org.tron.protos.Contract.FreezeBalanceContract;
import org.tron.protos.Contract.TriggerSmartContract;
import org.tron.protos.Protocol.Account;
import org.tron.protos.Protocol.Transaction;
import org.tron.protos.Protocol.TransactionInfo;

@Slf4j
public class WalletGrpcClient {

  private final ManagedChannel channel;
  private final WalletGrpc.WalletBlockingStub stub;
  private String host;

  public WalletGrpcClient(String host) {
    logger.info("Create gRPC client: {}.", host);

    this.host = host;

    channel = ManagedChannelBuilder.forTarget(host)
        .usePlaintext(true)
        .build();
    stub = WalletGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public Transaction createTransaction(Contract.TransferContract contract) {
    return stub.createTransaction(contract);
  }

  public Transaction createTransaction(FreezeBalanceContract contract) {
    return stub.freezeBalance(contract);
  }

  public Account getAccount(Account account) {
    return stub.getAccount(account);
  }

  public boolean broadcastTransaction(Transaction signaturedTransaction) {
    GrpcAPI.Return response = stub.broadcastTransaction(signaturedTransaction);
    if (!response.getResult()) {
      String hash = Sha256Hash.of(signaturedTransaction.getRawData().toByteArray()).toString();
      System.err.println(
          "hash:" + hash + ",code:" + response.getCode() + ",msg:" + response.getMessage());
    }
    return response.getResult();
  }

  public response_code broadcastTransactionRetry(Transaction signaturedTransaction) {
    GrpcAPI.Return response = stub.broadcastTransaction(signaturedTransaction);
    return response.getCode();
  }

  public TransactionInfo getTransactionInfoById(String txID) {
    ByteString bsTxId = ByteString.copyFrom(ByteArray.fromHexString(txID));
    BytesMessage request = BytesMessage.newBuilder().setValue(bsTxId).build();

    return stub.getTransactionInfoById(request);
  }

  public NumberMessage getTotalTransaction() {
    return stub.totalTransaction(EmptyMessage.newBuilder().build());
  }

  public TransactionExtention createAssetIssue2(AssetIssueContract contract) {
    return stub.createAssetIssue2(contract);
  }

  public TransactionExtention triggerContract(TriggerSmartContract contract) {
    return stub.triggerContract(contract);
  }

  public String getHost() {
    return host;
  }

  public Transaction createAssetIssue(AssetIssueContract contract) {
    return stub.createAssetIssue(contract);
  }
}
