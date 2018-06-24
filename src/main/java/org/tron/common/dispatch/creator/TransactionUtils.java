package org.tron.common.dispatch.creator;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol.Transaction;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class TransactionUtils {
  public synchronized static Transaction createTransaction(com.google.protobuf.Message message, ContractType contractType) {
    Transaction.raw.Builder transactionBuilder = Transaction.raw.newBuilder().addContract(
        Transaction.Contract.newBuilder().setType(contractType).setParameter(
            Any.pack(message)).build());
    Transaction transaction = Transaction.newBuilder().setRawData(transactionBuilder.build()).build();

    transaction = setReference(transaction, 0L, ByteArray.fromHexString("000000000000000019ed34554c93e7d1ff7e950ae22370e05679445bcb22edd5"));

    transaction = setExpiration(transaction, System.currentTimeMillis() + 12 * 3600 * 1_000L);

    return transaction;
  }

  private static Transaction setReference(Transaction transaction, long blockNum, byte[] blockHash) {
    byte[] refBlockNum = ByteArray.fromLong(blockNum);
    Transaction.raw rawData = transaction.getRawData().toBuilder()
        .setRefBlockHash(ByteString.copyFrom(ByteArray.subArray(blockHash, 8, 16)))
        .setRefBlockBytes(ByteString.copyFrom(ByteArray.subArray(refBlockNum, 6, 8)))
        .build();
    return transaction.toBuilder().setRawData(rawData).build();
  }

  public static Transaction setExpiration(Transaction transaction, long expiration) {
    Transaction.raw rawData = transaction.getRawData().toBuilder().setExpiration(expiration)
        .build();
    return transaction.toBuilder().setRawData(rawData).build();
  }
}
