package org.tron.common.dispatch.creator.commonCase;

import com.google.protobuf.ByteString;
import java.util.concurrent.atomic.AtomicLong;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.BadCaseTransactionCreator;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;

public class BadAssetIssueParticipateBalanceInsufficiencyCreator extends AbstractTransactionCreator implements BadCaseTransactionCreator {
  private AtomicLong serialNum = new AtomicLong(0);

  @Override
  protected Protocol.Transaction create() {
    Contract.ParticipateAssetIssueContract contract = Contract.ParticipateAssetIssueContract.newBuilder()
        .setAmount(Long.MAX_VALUE)
        .setAssetName(assetName)
        .setOwnerAddress(ownerAddress)
        .setToAddress(toAddress)
        .build();
    Protocol.Transaction transaction = client.getRpcCli().createParticipateAssetIssueTransaction(contract);
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;

  }
}
