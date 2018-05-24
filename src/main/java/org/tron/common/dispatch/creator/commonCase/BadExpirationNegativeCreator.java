package org.tron.common.dispatch.creator.commonCase;

import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.BadCaseTransactionCreator;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;

import java.util.concurrent.atomic.AtomicLong;

public class BadExpirationNegativeCreator extends AbstractTransactionCreator implements BadCaseTransactionCreator {
  private AtomicLong serialNum = new AtomicLong(0);

  @Override
  protected Protocol.Transaction create() {
    Contract.TransferAssetContract contract = Contract.TransferAssetContract.newBuilder()
        .setAssetName(assetName)
        .setOwnerAddress(ownerAddress)
        .setToAddress(toAddress)
        .setAmount(amount)
        .build();
    Protocol.Transaction transaction = client.getRpcCli().createTransferAssetTransaction(contract);
    transaction = transaction.toBuilder()
        .setRawData(
            transaction.getRawData().toBuilder()
                .setExpiration(-1L)
                .setTimestamp(serialNum.getAndIncrement())
                .build()
        )
        .build();
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;

  }
}
