package org.tron.common.dispatch.creator.commonCase;

import com.google.protobuf.ByteString;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.BadCaseTransactionCreator;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;

public class BadRefBlockRandomValueCreator extends AbstractTransactionCreator implements BadCaseTransactionCreator {
  private AtomicLong serialNum = new AtomicLong(0);

  private Random random = new Random(System.currentTimeMillis());

  @Override
  protected Protocol.Transaction create() {
    Contract.TransferAssetContract contract = Contract.TransferAssetContract.newBuilder()
        .setAssetName(assetName)
        .setOwnerAddress(ownerAddress)
        .setToAddress(toAddress)
        .setAmount(amount)
        .build();
    Protocol.Transaction transaction = client.getRpcCli().createTransferAssetTransaction(contract);

    byte[] data = new byte[64];
    random.nextBytes(data);
    transaction = transaction.toBuilder()
        .setRawData(
            transaction.getRawData().toBuilder()
                .setRefBlockBytes(ByteString.copyFrom(data))
                .setRefBlockHash(ByteString.copyFrom(data))
                .setRefBlockNum(random.nextLong())
                .build()
        )
        .build();
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;

  }
}
