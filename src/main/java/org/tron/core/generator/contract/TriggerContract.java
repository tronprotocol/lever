package org.tron.core.generator.contract;

import static org.tron.core.contract.CreateSmartContract.triggerCallContract;

import com.google.protobuf.ByteString;
import org.tron.common.utils.AbiUtil;
import org.tron.common.utils.TransactionUtils;
import org.tron.core.generator.Creator;
import org.tron.core.generator.TransactionAbstract;
import org.tron.program.Lever;
import org.tron.protos.Contract.TriggerSmartContract;
import org.tron.protos.Protocol.Transaction;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class TriggerContract extends TransactionAbstract implements Creator {

  private static final ByteString DATA = ByteString.copyFrom(org.bouncycastle.util.encoders.Hex.decode(AbiUtil.parseMethod("multiply(int256,int256)", "3,4", false)));

  @Override
  public Transaction create() {
    TriggerSmartContract contract = triggerCallContract(
        OWNER_ADDRESS,
        Lever.getContractAddress(),
        CONTRACT_VALUE,
        DATA
    );

    Transaction transaction = TransactionUtils
        .createTransaction(contract, ContractType.CreateSmartContract);

    transaction = transaction.toBuilder().setRawData(transaction.getRawData().toBuilder().setFeeLimit(FEE_LIMIT).build()).build();

    return TransactionUtils
        .signTransaction(transaction, PRIVATE_KEY);
  }
}
