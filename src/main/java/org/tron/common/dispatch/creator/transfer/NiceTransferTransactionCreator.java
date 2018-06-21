package org.tron.common.dispatch.creator.transfer;

import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.GoodCaseTransactonCreator;
import org.tron.common.dispatch.TransactionFactory;
import org.tron.common.dispatch.creator.CreatorCounter;
import org.tron.common.dispatch.creator.TransactionUtils;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class NiceTransferTransactionCreator extends AbstractTransferTransactionCreator implements GoodCaseTransactonCreator {
  @Override
  protected Protocol.Transaction create() {
    TransactionFactory.context.getBean(CreatorCounter.class).put(this.getClass().getName());

    Contract.TransferContract contract = Contract.TransferContract.newBuilder()
        .setOwnerAddress(ownerAddress)
        .setToAddress(toAddress)
        .setAmount(amount)
        .build();
    Protocol.Transaction transaction = TransactionUtils.createTransaction(contract, ContractType.TransferContract);

    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;
  }

}
