package org.tron.common.dispatch.creator.assetIssue;

import com.google.protobuf.ByteString;
import java.util.concurrent.atomic.AtomicLong;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.BadCaseTransactionCreator;
import org.tron.common.dispatch.creator.TransactionUtils;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class BadTransferAssetOwnerNotFoundCreator extends AbstractTransactionCreator implements BadCaseTransactionCreator {
  private AtomicLong serialNum = new AtomicLong(0);

  @Override
  protected Protocol.Transaction create() {
    Contract.TransferAssetContract contract = Contract.TransferAssetContract.newBuilder()
        .setAssetName(assetName)
        .setOwnerAddress(ByteString.copyFrom(ByteArray.fromHexString("a07777777777777777777777777777777777777777")))
        .setToAddress(toAddress)
        .setAmount(amount)
        .build();
    Protocol.Transaction transaction = TransactionUtils.createTransaction(contract, ContractType.AssetIssueContract);
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;

  }
}
