package org.tron.common.dispatch.creator.assetIssue;

import java.util.concurrent.atomic.AtomicLong;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.BadCaseTransactionCreator;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;

public class BadTransferAssetAmountNegativeCreator extends AbstractTransactionCreator implements BadCaseTransactionCreator {
  private AtomicLong serialNum = new AtomicLong(0);

  @Override
  protected Protocol.Transaction create() {
    Contract.TransferAssetContract contract = Contract.TransferAssetContract.newBuilder()
        .setAssetName(assetName)
        .setOwnerAddress(ownerAddress)
        .setToAddress(toAddress)
        .setAmount(-1L)
        .build();
    Protocol.Transaction transaction = client.getRpcCli().createTransferAssetTransaction(contract);
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;

  }
}
