package org.tron.program;

import static org.tron.common.utils.TransactionUtils.signTransaction;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.protobuf.ByteString;
import lombok.Getter;
import org.tron.Validator.LongValidator;
import org.tron.Validator.StringValidator;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Contract.FreezeBalanceContract;
import org.tron.protos.Contract.ResourceCode;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletGrpcClient;

public class FreezeBalance {

  private static final long FROZEN_DURATION = 3L;
  private static WalletGrpcClient client;

  public static void main(String[] args) {
    FreezeBalanceArgs argsObj = new FreezeBalanceArgs();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    client = new WalletGrpcClient(argsObj.getGRPC());

    boolean isSuccess = freezeBalance(argsObj.getPrivateKey(), argsObj.getFrozenBalance(),
        FROZEN_DURATION);

    if (isSuccess) {
      System.out.println("success");
    } else {
      System.out.println("failed");
    }
  }

  public static boolean freezeBalance(String privateKey, long frozen_balance,
      long frozen_duration) {
    ECKey ecKey = ECKey.fromPrivate(ByteArray.fromHexString(privateKey));

    Contract.FreezeBalanceContract contract = createFreezeBalanceContract(ecKey.getAddress(),
        frozen_balance,
        frozen_duration);

    Transaction transaction = client.createTransaction(contract);

    if (transaction == null || transaction.getRawData().getContractCount() == 0) {
      return false;
    }

    transaction = signTransaction(transaction, ecKey);
    return client.broadcastTransaction(transaction);
  }

  private static FreezeBalanceContract createFreezeBalanceContract(byte[] address,
      long frozen_balance,
      long frozen_duration) {
    Contract.FreezeBalanceContract.Builder builder = Contract.FreezeBalanceContract.newBuilder();
    ByteString byteAddress = ByteString.copyFrom(address);

    builder.setOwnerAddress(byteAddress).setFrozenBalance(frozen_balance)
        .setFrozenDuration(frozen_duration)
        .setResource(ResourceCode.ENERGY);

    return builder.build();
  }

  public static class FreezeBalanceArgs {

    @Getter
    @Parameter(names = {
        "--grpc"}, description = "gRPC host", required = true, validateWith = StringValidator.class)
    private String gRPC;

    @Getter
    @Parameter(names = {
        "--privatekey"}, description = "Private key", required = true, validateWith = StringValidator.class)
    private String privateKey;

    @Getter
    @Parameter(names = {
        "--frozenbalance"}, description = "Frozenbalance", required = true, validateWith = LongValidator.class)
    private long frozenBalance;
  }
}

