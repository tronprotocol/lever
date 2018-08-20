package org.tron.common.dispatch.creator.contract;

import static org.tron.core.contract.CreateSmartContract.triggerCallContract;

import java.util.concurrent.atomic.LongAdder;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.GoodCaseTransactonCreator;
import org.tron.common.dispatch.TransactionFactory;
import org.tron.common.dispatch.creator.CreatorCounter;
import org.tron.common.dispatch.creator.transfer.AbstractTransferTransactionCreator;
import org.tron.common.utils.AbiUtil;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.module.Account;
import org.tron.program.GenerateTransaction;
import org.tron.protos.Contract.TriggerSmartContract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class TriggerContractAirDropCreator extends AbstractTransferTransactionCreator implements GoodCaseTransactonCreator {
  LongAdder count = new LongAdder();

  @Override
  protected Protocol.Transaction create() {
    TransactionFactory.context.getBean(CreatorCounter.class).put(this.getClass().getName());
    Account account = GenerateTransaction
        .getAccounts().get(count.intValue() % GenerateTransaction.getAccounts().size());
    String addressBase58 = Base58.encode58Check(ByteArray.fromHexString(account.getAddress()));
    count.increment();

    String airDropMArgsStr = "\""+ addressBase58 +"\",\"1\"";

    TriggerSmartContract contract = triggerCallContract(ownerAddress.toByteArray(), Base58.decodeFromBase58Check(GenerateTransaction.getArgsObj().getContractAddress()), callValue, org.bouncycastle.util.encoders.Hex
        .decode(AbiUtil.parseMethod(airDropMethodStr, airDropMArgsStr, false)));

    Protocol.Transaction transaction = TransactionUtils.createTransaction(contract, ContractType.TriggerSmartContract);

    transaction = transaction.toBuilder().setRawData(transaction.getRawData().toBuilder().setFeeLimit(10000000).build()).build();

    transaction = TransactionUtils.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;
  }
}
