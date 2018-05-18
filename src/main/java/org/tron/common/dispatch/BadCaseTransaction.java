package org.tron.common.dispatch;

import org.tron.common.dispatch.strategy.Level2Strategy;

public abstract class BadCaseTransaction extends Level2Strategy {
  @Override
  protected Boolean nice() {
    return false;
  }
}
