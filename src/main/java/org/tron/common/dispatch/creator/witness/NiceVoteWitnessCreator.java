package org.tron.common.dispatch.creator.witness;

import com.google.protobuf.ByteString;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.BadCaseTransactionCreator;
import org.tron.common.dispatch.TransactionFactory;
import org.tron.common.dispatch.creator.CreatorCounter;
import org.tron.common.dispatch.creator.TransactionUtils;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.module.Account;
import org.tron.program.ExportDataFromFactory;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class NiceVoteWitnessCreator extends AbstractTransactionCreator implements
    BadCaseTransactionCreator {

  private AtomicLong serialNum = new AtomicLong(0);

  LongAdder count = new LongAdder();

  @Override
  protected Protocol.Transaction create() {
    Account account = ExportDataFromFactory.accounts.get(count.intValue() % ExportDataFromFactory.accounts.size());
    ByteString bytes = ByteString
        .copyFrom(ByteArray.fromHexString(account.getAddress()));
    count.increment();

    TransactionFactory.context.getBean(CreatorCounter.class).put(this.getClass().getName());
    Contract.VoteWitnessContract.Builder contractBuilder = Contract.VoteWitnessContract.newBuilder()
        .setOwnerAddress(bytes);

    for (String addressBase58 : voteWitnessMap.keySet()) {
      String value = voteWitnessMap.get(addressBase58);
      long count = Long.parseLong(value);
      Contract.VoteWitnessContract.Vote.Builder voteBuilder = Contract.VoteWitnessContract.Vote
          .newBuilder();
      byte[] address = Base58.decodeFromBase58Check(addressBase58);
      if (address == null) {
        continue;
      }
      voteBuilder.setVoteAddress(ByteString.copyFrom(address));
      voteBuilder.setVoteCount(count);
      contractBuilder.addVotes(voteBuilder.build());
    }

    Contract.VoteWitnessContract contract = contractBuilder.build();

    Protocol.Transaction transaction = TransactionUtils
        .createTransaction(contract, ContractType.VoteWitnessContract);
    transaction = client.signTransaction(transaction,
        ECKey.fromPrivate(ByteArray.fromHexString(account.getPrivateKey())));
    return transaction;
  }
}
