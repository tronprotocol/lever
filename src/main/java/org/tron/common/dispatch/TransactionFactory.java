package org.tron.common.dispatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.tron.common.dispatch.strategy.Dispatcher;
import org.tron.common.dispatch.strategy.Level1Strategy;
import org.tron.common.dispatch.strategy.Level2Strategy;
import org.tron.program.GenerateTransaction;
import org.tron.protos.Protocol;

import java.io.IOException;

public class TransactionFactory {
  public static ApplicationContext context;

  private static Dispatcher dispatcher;

  public static void init() {
    context = new FileSystemXmlApplicationContext(GenerateTransaction
        .getArgsObj().getContext());
    dispatcher = context.getBean(Dispatcher.class);
  }

  public static Protocol.Transaction newTransaction() {
    Level1Strategy level1Strategy = dispatcher.dispatch();
    if (level1Strategy == null) {
      return null;
    }

    Level2Strategy level2Strategy = level1Strategy.dispatch();
    if (level2Strategy == null) {
      return null;
    }

    return level2Strategy.dispatch();
  }

  public static Protocol.Transaction newTransaction(Class<? extends Level2Strategy> clz) {
    Level2Strategy level2Strategy = context.getBean(clz);
    return level2Strategy.dispatch();
  }

  public static void main(String[] args) throws IOException {
//    Protocol.Transaction createAssetTransaction = newTransaction(NiceCreateAssetTransactionCreator.class);
//
//    Protocol.Transaction freezeBlanceTransaction = newTransaction(NiceFreezeBalanceTransactionCreator.class);
//
//    Protocol.Transaction transferAssetTransaction = newTransaction(NiceTransferAssetTransactionCreator.class);
  }

}
