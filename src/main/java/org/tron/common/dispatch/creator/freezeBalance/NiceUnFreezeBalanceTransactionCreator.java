package org.tron.common.dispatch.creator.freezeBalance;

import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.GoodCaseTransactonCreator;
import org.tron.common.dispatch.TransactionFactory;
import org.tron.common.dispatch.creator.CreatorCounter;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class NiceUnFreezeBalanceTransactionCreator extends AbstractTransactionCreator implements GoodCaseTransactonCreator {

  @Override
  protected Protocol.Transaction create() {
    TransactionFactory.context.getBean(CreatorCounter.class).put(this.getClass().getName());
    Contract.UnfreezeBalanceContract contract = Contract.UnfreezeBalanceContract.newBuilder()
        .setOwnerAddress(ownerAddress)
        .build();
    Protocol.Transaction transaction = TransactionUtils.createTransaction(contract, ContractType.UnfreezeBalanceContract);
    transaction = TransactionUtils.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;
  }
}
