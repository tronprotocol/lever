package org.tron.common.dispatch;

import org.tron.common.dispatch.strategy.Level2Strategy;

public abstract class GoodCaseTransacton extends Level2Strategy {
  @Override
  protected Boolean nice() {
    return true;
  }
}
