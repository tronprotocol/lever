package org.tron.common.dispatch.creator.contract;

import static org.tron.core.contract.CreateSmartContract.createContractDeployContract;

import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.GoodCaseTransactonCreator;
import org.tron.common.dispatch.TransactionFactory;
import org.tron.common.dispatch.creator.CreatorCounter;
import org.tron.common.dispatch.creator.transfer.AbstractTransferTransactionCreator;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.protos.Contract.CreateSmartContract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class DeployContractTransactionCreator extends AbstractTransferTransactionCreator implements GoodCaseTransactonCreator {
  @Override
  protected Protocol.Transaction create() {
    TransactionFactory.context.getBean(CreatorCounter.class).put(this.getClass().getName());

    CreateSmartContract contract = createContractDeployContract(contractName, ownerAddress.toByteArray(),
        ABI, code, value, consumeUserResourcePercent, libraryAddress);

    Protocol.Transaction transaction = TransactionUtils.createTransaction(contract, ContractType.CreateSmartContract);

    transaction = transaction.toBuilder().setRawData(transaction.getRawData().toBuilder().setFeeLimit(10000000).build()).build();

    transaction = TransactionUtils.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;
  }
}
