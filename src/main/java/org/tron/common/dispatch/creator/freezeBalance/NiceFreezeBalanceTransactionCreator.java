package org.tron.common.dispatch.creator.freezeBalance;

import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.GoodCaseTransactonCreator;
import org.tron.common.dispatch.TransactionFactory;
import org.tron.common.dispatch.creator.CreatorCounter;
import org.tron.common.dispatch.creator.TransactionUtils;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class NiceFreezeBalanceTransactionCreator extends AbstractTransactionCreator implements GoodCaseTransactonCreator {

  @Override
  protected Protocol.Transaction create() {
    TransactionFactory.context.getBean(CreatorCounter.class).put(this.getClass().getName());
    Contract.FreezeBalanceContract contract = Contract.FreezeBalanceContract.newBuilder()
        .setOwnerAddress(ownerAddress)
        .setFrozenBalance(1000000 * 1000_000L)
        .setFrozenDuration(3)
        .build();
    Protocol.Transaction transaction = TransactionUtils.createTransaction(contract, ContractType.FreezeBalanceContract);
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;
  }
}
