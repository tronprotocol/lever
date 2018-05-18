package org.tron.common.dispatch.strategy;

import lombok.Setter;
import lombok.ToString;
import org.tron.protos.Protocol;

@ToString
public abstract class Level2Strategy extends Bucket implements IStrategy<Protocol.Transaction> {

  @Setter
  protected String name;
}
