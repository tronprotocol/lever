package org.tron.program;

import static org.tron.core.contract.CreateSmartContract.triggerCallContract;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.protobuf.ByteString;
import java.util.Objects;
import lombok.Getter;
import org.spongycastle.util.encoders.Hex;
import org.tron.Validator.StringValidator;
import org.tron.api.GrpcAPI.TransactionExtention;
import org.tron.common.utils.AbiUtil;
import org.tron.common.utils.Base58;
import org.tron.core.exception.EncodingException;
import org.tron.protos.Contract.TriggerSmartContract;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletGrpcClient;

public class GetContractAsset {

  private static WalletGrpcClient client;

  public static void main(String[] args) {
    Args argsObj = new Args();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    TriggerSmartContract contract = null;
    try {
      contract = triggerCallContract(
          ByteString.copyFrom(
              Objects.requireNonNull(
                  Base58.decodeFromBase58Check(
                      argsObj.getOwnerAddress()
                  )
              )
          ).toByteArray(),
          Base58.decodeFromBase58Check(
              argsObj.getContractAddress()
          ),
          0L,
          org.bouncycastle.util.encoders.Hex.decode(
              AbiUtil.parseMethod(argsObj.getMethodStr(), String.format("\"%s\"", argsObj.getArgsStr()), false)
          )
      );
    } catch (EncodingException e) {
      e.printStackTrace();
    }

    client = new WalletGrpcClient(argsObj.getGRPC());

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
        "--owner-address"}, required = true)
    private String ownerAddress;

    @Getter
    @Parameter(names = {
        "--contract-address"}, required = true)
    private String contractAddress;

    @Getter
    @Parameter(names = {
        "--method-str"}, required = true)
    private String methodStr;

    @Getter
    @Parameter(names = {
        "--args-str"}, required = true)
    private String argsStr;
  }
}
