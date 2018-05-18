package org.tron.common.dispatch.transferTransaction;

import org.tron.common.dispatch.GoodCaseTransacton;
import org.tron.protos.Protocol;

public class NiceTransferTransaction extends GoodCaseTransacton {
  @Override
  protected Protocol.Transaction create() {
    return null;
  }

}
