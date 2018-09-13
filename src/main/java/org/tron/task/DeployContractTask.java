package org.tron.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tron.common.config.Args;

public class DeployContractTask implements Task {

  private static final Logger logger = LoggerFactory.getLogger("DeployContractTask");

  @Override
  public void init(Args args) {
    logger.info("Create task: {}.", getClass().getSimpleName());
  }

  @Override
  public void start() {

  }

  @Override
  public void shutdown() {

  }
}
