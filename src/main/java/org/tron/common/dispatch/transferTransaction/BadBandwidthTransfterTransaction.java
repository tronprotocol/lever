package org.tron.common.dispatch.transferTransaction;

import org.tron.protos.Protocol;

public class BadBandwidthTransfterTransaction extends AbstractTransferTransaction {
  @Override
  protected Protocol.Transaction create() {
    return null;
  }

  @Override
  protected Boolean nice() {
    return false;
  }
}
