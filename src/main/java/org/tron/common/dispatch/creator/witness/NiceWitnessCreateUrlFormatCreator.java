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
import org.tron.common.utils.Utils;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;
import org.tron.service.WalletClient;

public class NiceWitnessCreateUrlFormatCreator extends AbstractTransactionCreator implements BadCaseTransactionCreator {
  private AtomicLong serialNum = new AtomicLong(0);

  @Override
  protected Protocol.Transaction create() {
    TransactionFactory.context.getBean(CreatorCounter.class).put(this.getClass().getName());
    ECKey ecKey = new ECKey(Utils.getRandom());

    // Generate new account
    Contract.TransferContract createAccountContract = Contract.TransferContract.newBuilder()
        .setOwnerAddress(ownerAddress)
        .setToAddress(ByteString.copyFrom(ecKey.getAddress()))
        .setAmount(amountOneTrx)
        .build();
    Protocol.Transaction createAccountTransaction = client.getRpcCli().createTransaction(createAccountContract);

    createAccountTransaction = client.signTransaction(createAccountTransaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));

    boolean b = TransactionFactory.context.getBean(WalletClient.class).getRpcCli()
        .broadcastTransaction(createAccountTransaction);

    Protocol.Transaction transaction = null;

    if (b) {
      Contract.WitnessCreateContract contract = Contract.WitnessCreateContract.newBuilder()
          .setOwnerAddress(ByteString.copyFrom(ecKey.getAddress()))
          .setUrl(ByteString.copyFrom(ByteArray.fromString("http://goodurlformat.org")))
          .build();
      transaction = TransactionUtils.createTransaction(contract, ContractType.WitnessCreateContract);
      transaction = client.signTransaction(transaction, ECKey.fromPrivate(ecKey.getPrivKeyBytes()));
    }

    return transaction;
  }
}
