package org.tron.task;

import static org.tron.core.contract.CreateSmartContract.triggerCallContract;

import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.spongycastle.util.encoders.Hex;
import org.tron.api.GrpcAPI.TransactionExtention;
import org.tron.common.config.Args;
import org.tron.common.utils.AbiUtil;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.core.exception.EncodingException;
import org.tron.protos.Contract.TriggerSmartContract;
import org.tron.protos.Protocol.Account;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletGrpcClient;

@Slf4j
public class GetVMResultTask implements Task {

  //private List<WalletGrpcClient> clients = new ArrayList<>();
  private Map<String, Account> accountMap;
  private Account account;

  private List<String> grpcHosts;
  private String accountAddress;
  private String owneraddress;
  private String methodStr;
  private String agrsStr;
  private WalletGrpcClient client;
  private Args args;

  public GetVMResultTask(List<String> grpcHosts, String accountAddress) {
    logger.info("Create task: {}.", getClass().getSimpleName());

    this.grpcHosts = grpcHosts;
    this.accountAddress = accountAddress;
  }

  @Override
  public void init(Args args) {
    logger.info("Init get account task.");

    accountMap = new HashMap<>();

    this.owneraddress = "27meR2d4HodFPYX2V8YRDrLuFpYdbLvBEWi";

    this.methodStr = "getBalance(address)";

    this.agrsStr = "27meR2d4HodFPYX2V8YRDrLuFpYdbLvBEWi";

    this.args = args;

    initGrpcClients();

    initCheckAccounts();
  }

  private void initGrpcClients() {
    logger.info("Init gRPC clients.");

    String grpcHost = this.args.getGRpcCheckAddress().get(0);
    client = new WalletGrpcClient(
        grpcHost);

  }

  private void initCheckAccounts() {
    logger.info("Init check accounts.");

    TriggerSmartContract contract = null;
    try {
      contract = triggerCallContract(
          ByteString.copyFrom(
              Objects.requireNonNull(
                  Base58.decodeFromBase58Check(
                      //argsObj.getOwnerAddress()
                      owneraddress
                  )
              )
          ).toByteArray(),
          Base58.decodeFromBase58Check(
              //argsObj.getContractAddress()
              ByteArray.toHexString(DeployAirContractTask.getContractAddress())
          ),
          0L,
          org.bouncycastle.util.encoders.Hex.decode(
              AbiUtil.parseMethod(methodStr, String.format("\"%s\"", agrsStr), false)
          )
      );

    } catch (EncodingException e) {
      e.printStackTrace();
    }

    //client = new WalletGrpcClient(argsObj.getGRPC());

    TransactionExtention transactionExtention = client.triggerContract(contract);

    if (transactionExtention == null || !transactionExtention.getResult().getResult()) {
      System.out.println("RPC create call trx failed!");
      System.out.println("Code = " + transactionExtention.getResult().getCode());
      System.out
          .println("Message = " + transactionExtention.getResult().getMessage().toStringUtf8());
      return;
    }

    Transaction transaction = transactionExtention.getTransaction();
    if (transaction.getRetCount() != 0 &&
        transactionExtention.getConstantResult(0) != null &&
        transactionExtention.getResult() != null) {
      byte[] result = transactionExtention.getConstantResult(0).toByteArray();
      Long count = Long.valueOf(Hex.toHexString(result), 16);
      System.out.println("success:" + count);
    }

  }

  @Override
  public void start() {
    logger.info("Start get account task.");

   // for (WalletGrpcClient client : clients) {
      accountMap.put(client.getHost(),
          client.getAccount(account));
   // }
  }

  @Override
  public void shutdown() {
    logger.info("Shutdown get account task.");

   // for (WalletGrpcClient client : clients) {
      try {
        client.shutdown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
 //   }
  }

  public Map<String, Account> getAccountMap() {
    return accountMap;
  }
}
