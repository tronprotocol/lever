package org.tron.program;

import static org.tron.core.contract.CreateSmartContract.createContractDeployContract;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.protobuf.ByteString;
import java.util.Objects;
import lombok.Getter;
import org.tron.Validator.StringValidator;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.protos.Contract.CreateSmartContract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;
import org.tron.service.WalletGrpcClient;

public class DeployContract {

  private static WalletGrpcClient client;

  public static void main(String[] args) {
    Args argsObj = new Args();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    CreateSmartContract contract = createContractDeployContract(
        argsObj.getContractName(),
        ByteString.copyFrom(
            Objects.requireNonNull(
                Base58.decodeFromBase58Check(
                    argsObj.getOwnerAddress()
                )
            )
        ).toByteArray(),
        argsObj.getAbi(),
        argsObj.getCode(),
        0L,
        100,
        null
    );

    Protocol.Transaction transaction = TransactionUtils
        .createTransaction(contract, ContractType.CreateSmartContract);

    transaction = transaction.toBuilder()
        .setRawData(transaction.getRawData().toBuilder().setFeeLimit(argsObj.getFeeLimit()).build()).build();

    transaction = TransactionUtils
        .signTransaction(transaction,
            ECKey.fromPrivate(ByteArray.fromHexString(argsObj.getPrivateKey())));

    client = new WalletGrpcClient(argsObj.getGRPC());

    boolean isSuccess = client.broadcastTransaction(transaction);

    if (isSuccess) {
      System.out.println(
          "success:" + Base58
              .encode58Check(TransactionUtils.generateContractAddress(Objects
                  .requireNonNull(Base58.decodeFromBase58Check(
                      argsObj.getOwnerAddress()
                  )), transaction)));
    } else {
      System.out.println("failed");
    }
  }

  private static class Args {

    @Getter
    @Parameter(names = {
        "--grpc"}, description = "gRPC host", required = true, validateWith = StringValidator.class)
    private String gRPC;

    @Getter
    @Parameter(names = {
        "--private-key"}, required = true)
    private String privateKey;

    @Getter
    @Parameter(names = {
        "--contract-name"}, required = true)
    private String contractName;

    @Getter
    @Parameter(names = {
        "--owner-address"}, required = true)
    private String ownerAddress;

    @Getter
    @Parameter(names = {
        "--abi"}, required = true)
    private String abi;

    @Getter
    @Parameter(names = {
        "--code"}, required = true)
    private String code;

    @Getter
    @Parameter(names = {
        "--feeLimit"}, required = true)
    private long feeLimit;
  }
}
