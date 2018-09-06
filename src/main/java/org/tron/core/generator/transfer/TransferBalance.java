package org.tron.core.generator.transfer;

import org.tron.common.utils.TransactionUtils;
import org.tron.core.generator.Creator;
import org.tron.core.generator.TransactionAbstract;
import org.tron.protos.Contract.TransferContract;
import org.tron.protos.Protocol.Transaction;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class TransferBalance extends TransactionAbstract implements Creator {

  @Override
  public Transaction create() {

    TransferContract contract = TransferContract.newBuilder()
        .setOwnerAddress(OWNER_ADDRESS)
        .setToAddress(TO_ADDRESS)
        .setAmount(AMOUNT)
        .build();

    Transaction transaction = TransactionUtils
        .createTransaction(contract, ContractType.TransferContract);

    return TransactionUtils
        .signTransaction(transaction, PRIVATE_KEY);
  }
}
