package org.tron.task;

import lombok.extern.slf4j.Slf4j;
import org.tron.common.config.Args;

@Slf4j
public class CreateAssetTask implements Task {

  @Override
  public void init(Args args) {
    logger.info("Create task: {}.", getClass().getSimpleName());
    logger.info("Init create asset task.");
  }

  @Override
  public void start() {
    logger.info("Start create asset task.");
  }

  @Override
  public void shutdown() {
    logger.info("Shutdown create asset task.");
  }
}
