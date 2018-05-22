package org.tron.common.dispatch.transfer;

import org.tron.common.dispatch.BadCaseTransactionCreator;
import org.tron.protos.Protocol;

public class BadBandwidthTransfterTransactionCreator extends AbstractTransferTransactionCreator implements BadCaseTransactionCreator {

  @Override
  protected Protocol.Transaction create() {
    return null;
  }

}
