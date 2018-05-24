package org.tron.common.dispatch.creator.assetIssue;

import com.google.common.base.Charsets;
import com.google.protobuf.ByteString;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.GoodCaseTransactonCreator;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;

public class NiceCreateAssetTransactionCreator extends AbstractTransactionCreator implements GoodCaseTransactonCreator {

  @Override
  protected Protocol.Transaction create() {
    Contract.AssetIssueContract contract = Contract.AssetIssueContract.newBuilder()
        .setOwnerAddress(ownerAddress)
        .setDescription(assetName)
        .setName(assetName)
        .setUrl(ByteString.copyFrom(assetName.toStringUtf8() + ".com", Charsets.UTF_8))
        .setStartTime(System.currentTimeMillis() + 3600 * 1000L)
        .setEndTime(System.currentTimeMillis() + 2 * 86400 * 1000L)
        .setTotalSupply(1000000)
        .setNum(1)
        .setTrxNum(1)
        .build();
    Protocol.Transaction transaction = client.getRpcCli().createAssetIssue(contract);
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;
  }
}
