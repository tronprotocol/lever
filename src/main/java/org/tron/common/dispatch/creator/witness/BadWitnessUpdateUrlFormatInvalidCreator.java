package org.tron.common.dispatch.creator.witness;

import com.google.protobuf.ByteString;
import java.util.concurrent.atomic.AtomicLong;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.BadCaseTransactionCreator;
import org.tron.common.dispatch.TransactionFactory;
import org.tron.common.dispatch.creator.CreatorCounter;
import org.tron.common.dispatch.creator.TransactionUtils;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class BadWitnessUpdateUrlFormatInvalidCreator extends AbstractTransactionCreator implements BadCaseTransactionCreator {
  private AtomicLong serialNum = new AtomicLong(0);

  @Override
  protected Protocol.Transaction create() {
    TransactionFactory.context.getBean(CreatorCounter.class).put(this.getClass().getName());
    Contract.WitnessUpdateContract contract = Contract.WitnessUpdateContract.newBuilder()
        .setOwnerAddress(witnessAddress)
        .setUpdateUrl(ByteString.copyFrom(ByteArray.fromString("invalidUrl")))
        .build();
    Protocol.Transaction transaction = TransactionUtils.createTransaction(contract, ContractType.WitnessUpdateContract);
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(witnessPrivateKey)));
    return transaction;
  }
}
