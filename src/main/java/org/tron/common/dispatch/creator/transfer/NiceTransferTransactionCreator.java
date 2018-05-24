package org.tron.common.dispatch.creator.transfer;

import org.tron.common.dispatch.GoodCaseTransactonCreator;
import org.tron.protos.Protocol;

public class NiceTransferTransactionCreator extends AbstractTransferTransactionCreator implements GoodCaseTransactonCreator {
  @Override
  protected Protocol.Transaction create() {
    return null;
  }

}
