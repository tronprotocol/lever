package org.tron.common.dispatch.creator.transfer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.BadCaseTransactionCreator;
import org.tron.common.dispatch.creator.TransactionUtils;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class BadTransferAmountMinLongValueCreator extends AbstractTransactionCreator implements BadCaseTransactionCreator {
  private AtomicLong serialNum = new AtomicLong(0);

  private Random random = new Random(System.currentTimeMillis());

  @Override
  protected Protocol.Transaction create() {
    Contract.TransferContract contract = Contract.TransferContract.newBuilder()
        .setOwnerAddress(ownerAddress)
        .setToAddress(toAddress)
        .setAmount(Long.MIN_VALUE)
        .build();
    Protocol.Transaction transaction = TransactionUtils.createTransaction(contract, ContractType.TransferContract);

    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;

  }
}
