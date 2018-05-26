package org.tron.common.dispatch.creator.commonCase;

import java.util.concurrent.atomic.AtomicLong;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.BadCaseTransactionCreator;
import org.tron.common.dispatch.creator.TransactionUtils;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Time;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class BadExpirationMaxLongValueCreator extends AbstractTransactionCreator implements BadCaseTransactionCreator {
  private AtomicLong serialNum = new AtomicLong(0);

  @Override
  protected Protocol.Transaction create() {
    Contract.TransferContract contract = Contract.TransferContract.newBuilder()
        .setOwnerAddress(ownerAddress)
        .setToAddress(toAddress)
        .setAmount(amount)
        .build();
    Protocol.Transaction transaction = TransactionUtils.createTransaction(contract, ContractType.TransferContract);
    transaction = transaction.toBuilder()
        .setRawData(
            transaction.getRawData().toBuilder()
                .setExpiration(Long.MAX_VALUE)
                .setTimestamp(serialNum.getAndIncrement())
                .build()
        )
        .build();
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;

  }
}
