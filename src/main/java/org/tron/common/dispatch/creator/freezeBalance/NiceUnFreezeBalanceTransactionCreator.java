package org.tron.common.dispatch.creator.freezeBalance;

import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.GoodCaseTransactonCreator;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;

public class NiceUnFreezeBalanceTransactionCreator extends AbstractTransactionCreator implements GoodCaseTransactonCreator {

  @Override
  protected Protocol.Transaction create() {
    Contract.UnfreezeBalanceContract contract = Contract.UnfreezeBalanceContract.newBuilder()
        .setOwnerAddress(ownerAddress)
        .build();
    Protocol.Transaction transaction = client.getRpcCli().createTransaction(contract);
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;
  }
}
