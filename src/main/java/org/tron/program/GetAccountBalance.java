package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.protobuf.ByteString;
import java.util.Objects;
import lombok.Getter;
import org.tron.Validator.StringValidator;
import org.tron.common.utils.Base58;
import org.tron.protos.Protocol.Account;
import org.tron.service.WalletGrpcClient;

public class GetAccountBalance {

  private static WalletGrpcClient client;

  public static void main(String[] args) {
    Args argsObj = new Args();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    client = new WalletGrpcClient(argsObj.getGRPC());

    Account account = client.getAccount(
        Account.newBuilder()
            .setAddress(
                ByteString.copyFrom(
                    Objects.requireNonNull(
                        Base58.decodeFromBase58Check(argsObj.getAddress())
                    )
                )
            ).build()
    );

    System.out.println(account.getBalance());
  }

  public static class Args {

    @Getter
    @Parameter(names = {
        "--grpc"}, description = "gRPC host", required = true, validateWith = StringValidator.class)
    private String gRPC;

    @Getter
    @Parameter(names = {
        "--address"}, description = "Account address", required = true, validateWith = StringValidator.class)
    private String address;
  }
}
