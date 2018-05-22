package org.tron.common.dispatch.freezeBalance;

import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.GoodCaseTransactonCreator;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;

public class NiceFreezeBalanceTransactionCreator extends AbstractTransactionCreator implements GoodCaseTransactonCreator {

  @Override
  protected Protocol.Transaction create() {
    Contract.FreezeBalanceContract contract = Contract.FreezeBalanceContract.newBuilder()
        .setOwnerAddress(ownerAddress)
        .setFrozenBalance(1000000 * 1000_000L)
        .setFrozenDuration(3)
        .build();
    Protocol.Transaction transaction = client.getRpcCli().createTransaction(contract);
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;
  }
}
