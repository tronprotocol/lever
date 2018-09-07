package org.tron.common.dispatch.creator.contract;

import static org.tron.core.contract.CreateSmartContract.triggerCallContract;

import java.util.concurrent.atomic.AtomicInteger;
import org.tron.common.crypto.ECKey;
import org.tron.common.dispatch.GoodCaseTransactonCreator;
import org.tron.common.dispatch.TransactionFactory;
import org.tron.common.dispatch.creator.CreatorCounter;
import org.tron.common.dispatch.creator.transfer.AbstractTransferTransactionCreator;
import org.tron.common.utils.AbiUtil;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.core.exception.EncodingException;
import org.tron.program.GenerateTransaction;
import org.tron.protos.Contract.TriggerSmartContract;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class TriggerContractStorageCreator extends AbstractTransferTransactionCreator implements GoodCaseTransactonCreator {
  private static AtomicInteger count = new AtomicInteger();

  @Override
  protected Protocol.Transaction create() {
    TransactionFactory.context.getBean(CreatorCounter.class).put(this.getClass().getName());

    int andIncrement = count.getAndIncrement();

    TriggerSmartContract contract = null;
    try {
      contract = triggerCallContract(ownerAddress.toByteArray(), Base58
          .decodeFromBase58Check(GenerateTransaction.getArgsObj().getContractAddress()), 0L, org.bouncycastle.util.encoders.Hex
          .decode(AbiUtil.parseMethod("testInt2Str(uint256,string)", String.format("\"%d\",\"%d_index_string_abcabcabc\"", andIncrement, andIncrement), false)));
    } catch (EncodingException e) {
      e.printStackTrace();
    }

    Protocol.Transaction transaction = TransactionUtils.createTransaction(contract, ContractType.TriggerSmartContract);

    transaction = transaction.toBuilder().setRawData(transaction.getRawData().toBuilder().setFeeLimit(1000000000L).build()).build();

    transaction = TransactionUtils.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString(privateKey)));
    return transaction;
  }

}
