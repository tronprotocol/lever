package org.tron.common.dispatch.transferTransaction;

import com.google.protobuf.ByteString;
import org.tron.common.dispatch.Level2Strategy;

public abstract class AbstractTransferTransaction extends Level2Strategy {
  protected ByteString from;
  protected ByteString to;
  protected int account;
}
