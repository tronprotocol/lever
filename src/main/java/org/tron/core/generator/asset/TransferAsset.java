package org.tron.core.generator.asset;

import com.google.common.base.Charsets;
import com.google.protobuf.ByteString;
import org.tron.common.utils.TransactionUtils;
import org.tron.core.generator.Creator;
import org.tron.core.generator.TransactionAbstract;
import org.tron.protos.Contract.TransferAssetContract;
import org.tron.protos.Protocol.Transaction;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class TransferAsset extends TransactionAbstract implements Creator {

  private static final ByteString ASSET_NAME = ByteString.copyFrom("pressure1", Charsets.UTF_8);

  @Override
  public Transaction create() {

    TransferAssetContract contract = TransferAssetContract.newBuilder()
        .setAssetName(ASSET_NAME)
        .setOwnerAddress(OWNER_ADDRESS)
        .setToAddress(TO_ADDRESS)
        .setAmount(AMOUNT)
        .build();

    Transaction transaction = TransactionUtils
        .createTransaction(contract, ContractType.TransferAssetContract);

    return TransactionUtils
        .signTransaction(transaction, PRIVATE_KEY);
  }
}
