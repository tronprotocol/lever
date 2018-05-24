package org.tron.common.dispatch.creator.assetIssue;

import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.GoodCaseTransactonCreator;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;

import java.util.concurrent.atomic.AtomicLong;

public class NiceTransferAssetTransactionCreator extends AbstractTransactionCreator implements GoodCaseTransactonCreator {
  AtomicLong serialNum = new AtomicLong(0);
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
        .setRawData(transaction.getRawData().toBuilder()
            .setExpiration(transaction.getRawData().getExpiration() + 12 * 60 * 60 * 1_000L)
            .setTimestamp(serialNum.getAndIncrement())
            .build())
        .build();
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;
  }
}
