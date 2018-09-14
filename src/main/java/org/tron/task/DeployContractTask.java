package org.tron.task;

import lombok.extern.slf4j.Slf4j;
import org.tron.common.config.Args;

@Slf4j
public class DeployContractTask implements Task {

  @Override
  public void init(Args args) {
    logger.info("Create task: {}.", getClass().getSimpleName());
    logger.info("Init deploy contract task.");
  }

  @Override
  public void start() {
    logger.info("Start deploy contract task.");
  }

  @Override
  public void shutdown() {
    logger.info("Shutdown deploy contract task.");
  }
}
