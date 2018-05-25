package org.tron.common.dispatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tron.common.dispatch.creator.account.BadAccountUpdateNameEmptyCreator;
import org.tron.common.dispatch.creator.account.BadAccountUpdateNameHiddenCharactersCreator;
import org.tron.common.dispatch.creator.assetIssue.BadAssetIssueParticipateBalanceInsufficiencyCreator;
import org.tron.common.dispatch.creator.assetIssue.NiceCreateAssetTransactionCreator;
import org.tron.common.dispatch.creator.assetIssue.NiceTransferAssetTransactionCreator;
import org.tron.common.dispatch.creator.commonCase.BadExpirationCurrentOneDayCreator;
import org.tron.common.dispatch.creator.commonCase.BadExpirationMaxLongValueCreator;
import org.tron.common.dispatch.creator.commonCase.BadExpirationMinLongValueCreator;
import org.tron.common.dispatch.creator.commonCase.BadExpirationNegativeCreator;
import org.tron.common.dispatch.creator.commonCase.BadExpirationZeroCreator;
import org.tron.common.dispatch.creator.commonCase.BadRefBlockNullValueCreator;
import org.tron.common.dispatch.creator.commonCase.BadRefBlockRandomValueCreator;
import org.tron.common.dispatch.creator.commonCase.NiceExpirationCurrentCreator;
import org.tron.common.dispatch.creator.commonCase.NiceExpirationCurrentHalfDayCreator;
import org.tron.common.dispatch.creator.commonCase.NiceRefBlockServerValueCreator;
import org.tron.common.dispatch.creator.freezeBalance.NiceFreezeBalanceTransactionCreator;
import org.tron.common.dispatch.creator.freezeBalance.NiceUnFreezeBalanceTransactionCreator;
import org.tron.common.dispatch.creator.transfer.BadTransferAmountMaxLongValueCreator;
import org.tron.common.dispatch.creator.transfer.BadTransferAmountMinLongValueCreator;
import org.tron.common.dispatch.creator.transfer.BadTransferAmountNegativeCreator;
import org.tron.common.dispatch.creator.transfer.BadTransferAmountZeroCreator;
import org.tron.common.dispatch.creator.transfer.BadTransferOwnerNotFoundCreator;
import org.tron.common.dispatch.creator.transfer.NiceTransferToNotFoundCreator;
import org.tron.common.dispatch.creator.witness.BadWitnessCreateExistCreator;
import org.tron.common.dispatch.creator.witness.BadWitnessCreateUrlFormatInvalidCreator;
import org.tron.common.dispatch.creator.witness.BadWitnessUpdateUrlFormatInvalidCreator;
import org.tron.common.dispatch.creator.witness.NiceWitnessCreateUrlFormatCreator;
import org.tron.common.dispatch.creator.witness.NiceWitnessUpdateUrlFormatCreator;
import org.tron.common.dispatch.strategy.Dispatcher;
import org.tron.common.dispatch.strategy.Level1Strategy;
import org.tron.common.dispatch.strategy.Level2Strategy;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletClient;

import java.io.IOException;

public class TransactionFactory {
  public static final ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

  private static final Dispatcher dispatcher = context.getBean(Dispatcher.class);

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

    // account
    Transaction badAccountUpdateNameEmptyTransaction = newTransaction(BadAccountUpdateNameEmptyCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badAccountUpdateNameEmptyTransaction);

    Transaction badAccountUpdateNameHiddenCharactersTransaction = newTransaction(BadAccountUpdateNameHiddenCharactersCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badAccountUpdateNameHiddenCharactersTransaction);

    // assetIssue

    // commonCase
    Transaction badExpirationCurrentOneDayTransaction = newTransaction(BadExpirationCurrentOneDayCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badExpirationCurrentOneDayTransaction);

    Transaction badExpirationMaxLongValueTransaction = newTransaction(BadExpirationMaxLongValueCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badExpirationMaxLongValueTransaction);

    Transaction badExpirationMinLongValueTransaction = newTransaction(BadExpirationMinLongValueCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badExpirationMinLongValueTransaction);

    Transaction badExpirationNegativeTransaction = newTransaction(BadExpirationNegativeCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badExpirationNegativeTransaction);

    Transaction badExpirationZeroTransaction = newTransaction(BadExpirationZeroCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badExpirationZeroTransaction);

    Transaction badRefBlockNullValueTransaction = newTransaction(BadRefBlockNullValueCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badRefBlockNullValueTransaction);

    Transaction badRefBlockRandomValueTransaction = newTransaction(BadRefBlockRandomValueCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badRefBlockRandomValueTransaction);

    Transaction niceExpirationCurrentTransaction = newTransaction(NiceExpirationCurrentCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(niceExpirationCurrentTransaction);

    Transaction niceExpirationCurrentHalfDayTransaction = newTransaction(NiceExpirationCurrentHalfDayCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(niceExpirationCurrentHalfDayTransaction);

    Transaction niceRefBlockServerValueTransaction = newTransaction(NiceRefBlockServerValueCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(niceRefBlockServerValueTransaction);

    // freezeBalance
    Transaction niceFreezeBalanceTransactionTransaction = newTransaction(NiceFreezeBalanceTransactionCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(niceFreezeBalanceTransactionTransaction);

    Transaction niceUnFreezeBalanceTransactionTransaction = newTransaction(NiceUnFreezeBalanceTransactionCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(niceUnFreezeBalanceTransactionTransaction);

    // transfer
    Transaction badTransferAmountMaxLongValueTransaction = newTransaction(BadTransferAmountMaxLongValueCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badTransferAmountMaxLongValueTransaction);

    Transaction badTransferAmountMinLongValueTransaction = newTransaction(BadTransferAmountMinLongValueCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badTransferAmountMinLongValueTransaction);

    Transaction badTransferAmountNegativeTransaction = newTransaction(BadTransferAmountNegativeCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badTransferAmountNegativeTransaction);

    Transaction badTransferAmountZeroTransaction = newTransaction(BadTransferAmountZeroCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badTransferAmountZeroTransaction);

    Transaction badTransferOwnerNotFoundTransaction = newTransaction(BadTransferOwnerNotFoundCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badTransferOwnerNotFoundTransaction);

    Transaction niceTransferToNotFoundTransaction = newTransaction(NiceTransferToNotFoundCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(niceTransferToNotFoundTransaction);

    // witness
    Transaction badWitnessCreateExistTransaction = newTransaction(BadWitnessCreateExistCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badWitnessCreateExistTransaction);

    Transaction badWitnessCreateUrlFormatInvalidTransaction = newTransaction(BadWitnessCreateUrlFormatInvalidCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badWitnessCreateUrlFormatInvalidTransaction);

    Transaction badWitnessUpdateUrlFormatInvalidTransaction = newTransaction(BadWitnessUpdateUrlFormatInvalidCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(badWitnessUpdateUrlFormatInvalidTransaction);

    Transaction niceWitnessCreateUrlFormatTransaction = newTransaction(NiceWitnessCreateUrlFormatCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(niceWitnessCreateUrlFormatTransaction);

    Transaction niceWitnessUpdateUrlFormatTransaction = newTransaction(NiceWitnessUpdateUrlFormatCreator.class);
    TransactionFactory.context.getBean(WalletClient.class).getRpcCli().broadcastTransaction(niceWitnessUpdateUrlFormatTransaction);
  }

}
