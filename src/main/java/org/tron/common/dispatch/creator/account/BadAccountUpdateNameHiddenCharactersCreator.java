package org.tron.common.dispatch.creator.account;

import com.google.protobuf.ByteString;
import java.util.concurrent.atomic.AtomicLong;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.AbstractTransactionCreator;
import org.tron.common.dispatch.BadCaseTransactionCreator;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol;

public class BadAccountUpdateNameHiddenCharactersCreator extends AbstractTransactionCreator implements BadCaseTransactionCreator {
  private AtomicLong serialNum = new AtomicLong(0);

  @Override
  protected Protocol.Transaction create() {
    Contract.AccountUpdateContract contract = Contract.AccountUpdateContract.newBuilder()
        .setAccountName(ByteString.copyFrom(ByteArray.fromString("\n\t\n\t\r\b")))
        .setOwnerAddress(ownerAddress)
        .build();
    Protocol.Transaction transaction = client.getRpcCli().updateAccount(contract);
    transaction = client.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;

  }
}
