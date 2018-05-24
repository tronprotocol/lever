package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;
import org.tron.Validator.LongValidator;
import org.tron.Validator.StringValidator;
import org.tron.service.WalletClient;

public class FreezaBalance {

  private static final long FROZEN_DURATION = 3L;

  public static void main(String[] args) {
    FreezaBalanceArgs argsObj = new FreezaBalanceArgs();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    WalletClient walletClient = new WalletClient();
    walletClient.init(0);

    boolean isSuccess = walletClient
        .freezeBalance(argsObj.getPrivateKey(), argsObj.getFrozenBalance(), FROZEN_DURATION);

    if (isSuccess) {
      System.out.println("success");
    } else {
      System.out.println("failed");
    }
  }
}

class FreezaBalanceArgs {

  @Getter
  @Parameter(names = {
      "--privatekey"}, description = "Private key", required = true, validateWith = StringValidator.class)
  private String privateKey;

  @Getter
  @Parameter(names = {
      "--frozenbalance"}, description = "Frozenbalance", required = true, validateWith = LongValidator.class)
  private long frozenBalance;
}