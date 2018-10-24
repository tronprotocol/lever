package org.tron.task;

import static org.tron.core.contract.CreateSmartContract.createContractDeployContract;

import com.beust.jcommander.JCommander;
import com.google.protobuf.ByteString;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.tron.common.config.Args;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.program.DeployContract;
import org.tron.protos.Contract.CreateSmartContract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;
import org.tron.service.WalletGrpcClient;

@Slf4j
public class DeployContractTask implements Task {

  private WalletGrpcClient client;
  private Args args;
  private String contractName;
  private String ownerAddress;
  private String Abi;
  private String Code;
  private long feeLimit;
  private String OwnerPrivateKey;
  private static byte[] contractId;

  public DeployContractTask(){
    logger.info("Create task: {}.", getClass().getSimpleName());
  }

  @Override
  public void init(Args args) {
    logger.info("Init deploy contract task.");
    contractName = "createContract";
    ownerAddress = "27meR2d4HodFPYX2V8YRDrLuFpYdbLvBEWi";
    Abi = "[{\"constant\":false,\"inputs\":[{\"name\":\"a\",\"type\":\"int256\"},"
        + "{\"name\":\"b\",\"type\":\"int256\"}],\"name\":\"multiply\",\"outputs\":[{\"name\":\"output\",\"type\":\"int256\"}],"
        + "\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},"
        + "{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"from\",\"type\":\"address\"},"
        + "{\"indexed\":false,\"name\":\"a\",\"type\":\"int256\"},{\"indexed\":false,\"name\":\"b\",\"type\":\"int256\"},"
        + "{\"indexed\":false,\"name\":\"output\",\"type\":\"int256\"}],\"name\":\"MultiplyEvent\",\"type\":\"event\"}]";
    Code = "6080604052348015600f57600080fd5b5060e98061001e6000396000f300608060405260043610603e5763ffffffff7c0100000000000"
        + "0000000000000000000000000000000000000000000006000350416633c4308a881146043575b600080fd5b348015604e57600080fd5b5060"
        + "5b600435602435606d565b60408051918252519081900360200190f35b6040805133815260208101849052808201839052838302606082018"
        + "1905291517feb4e4c25ee4bb2b9466eb38f13989c0c221efa6f1c631b8b4820f00afcf5a3e59181900360800190a1929150505600a165627a7"
        + "a723058200dbf85f2b87350cd0aaa578b300b50d62fb3508880a151d2db70356c1fe463da0029";
    feeLimit = 100000000;
    OwnerPrivateKey = "cbe57d98134c118ed0d219c0c8bc4154372c02c1e13b5cce30dd22ecd7bed19e";
    this.args = args;
  }

  @Override
  public void start() {
    logger.info("Start deploy contract task.");
    CreateSmartContract contract = createContractDeployContract(
        contractName,
        ByteString.copyFrom(
            Objects.requireNonNull(
                Base58.decodeFromBase58Check(ownerAddress))).toByteArray(),
        Abi,
        Code,
        0L,
        100,
        null
    );

    Protocol.Transaction transaction = TransactionUtils
        .createTransaction(contract, ContractType.CreateSmartContract);

    transaction = transaction.toBuilder()
        .setRawData(transaction.getRawData().toBuilder().setFeeLimit(feeLimit).build()).build();

    transaction = TransactionUtils
        .signTransaction(transaction,
            ECKey.fromPrivate(ByteArray.fromHexString(OwnerPrivateKey)));

    String grpcHost = this.args.getGRpcCheckAddress().get(0);
    client = new WalletGrpcClient(
        grpcHost);
    boolean isSuccess = client.broadcastTransaction(transaction);
    if (isSuccess) {
      System.out.println(
          "success:" + Base58
              .encode58Check(TransactionUtils.generateContractAddress(Objects
                  .requireNonNull(Base58.decodeFromBase58Check(ownerAddress)), transaction)));

      contractId = TransactionUtils.generateContractAddress(Objects
          .requireNonNull(Base58.decodeFromBase58Check(ownerAddress)), transaction);

    } else {
      System.out.println("failed");
    }

    try {
      Thread.sleep(15000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  public static byte[] getContractId() {
    return contractId;
  }

  @Override
  public void shutdown() {
    logger.info("Shutdown deploy contract task.");
    try {
      client.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
