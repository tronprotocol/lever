package org.tron.common.dispatch.strategy;

import org.tron.common.dispatch.strategy.Bucket;
import org.tron.common.dispatch.strategy.IStrategy;
import org.tron.protos.Protocol;

public abstract class Level2Strategy extends Bucket implements IStrategy<Protocol.Transaction> {
}
