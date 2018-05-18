package org.tron.common.dispatch.transferTransaction;

import org.tron.common.dispatch.BadCaseTransaction;
import org.tron.protos.Protocol;

public class BadBandwidthTransfterTransaction extends BadCaseTransaction {
  @Override
  protected Protocol.Transaction create() {
    return null;
  }

}
