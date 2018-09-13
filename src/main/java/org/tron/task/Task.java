package org.tron.task;

import org.tron.common.config.Args;

public interface Task {
  void init(Args args);
  void start();
  void shutdown();
}
