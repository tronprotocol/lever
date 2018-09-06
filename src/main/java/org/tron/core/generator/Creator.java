package org.tron.core.generator;

import org.tron.protos.Protocol.Transaction;

public interface Creator {
  Transaction create();
}
