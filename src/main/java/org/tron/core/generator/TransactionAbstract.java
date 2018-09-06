package org.tron.core.generator;

import com.google.protobuf.ByteString;
import java.util.Objects;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;

public abstract class TransactionAbstract {

  // Common.
  protected static final ByteString OWNER_ADDRESS = ByteString
      .copyFrom(Objects
          .requireNonNull(Base58.decodeFromBase58Check("27meR2d4HodFPYX2V8YRDrLuFpYdbLvBEWi")));

  protected static final ByteString TO_ADDRESS = ByteString
      .copyFrom(Objects
          .requireNonNull(Base58.decodeFromBase58Check("27ZESitosJfKouTBrGg6Nk5yEjnJHXMbkZp")));

  protected static final long AMOUNT = 1L;

  protected static final ECKey PRIVATE_KEY = ECKey.fromPrivate(ByteArray
      .fromHexString("cbe57d98134c118ed0d219c0c8bc4154372c02c1e13b5cce30dd22ecd7bed19e"));

  // Contract.
  protected static final String CONTRACT_NAME = "createContract";
  protected static final long CONTRACT_VALUE = 0L;
  protected static final long CONSUME_USER_RESOURCE_PERCENT = 100L;
  protected static final String LIBRARY_ADDRESS = null;
  protected static final long FEE_LIMIT = 10000000L;
}
