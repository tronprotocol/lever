package org.tron.module;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.tron.common.config.Args;
import org.tron.common.crypto.Hash;
import org.tron.task.CheckResultTask;
import org.tron.task.CreateAssetTask;
import org.tron.task.GenerateTransactionTask;
import org.tron.task.SendTransactionTask;
import org.tron.task.Task;

@Slf4j
public class Application {

  private List<Task> taskList;

  private Args args;

  public Application(Args args) {
    this.args = args;
  }

  public void init() {
    logger.info("Init application.");

    initNetType(args.getNetType());
    initTasks(args);
  }

  private boolean initNetType(String netType) {
    logger.info("Init net type: {}.", netType);

    if (netType.equalsIgnoreCase("mainnet")) {
      Hash.changeAddressPrefixMainnet();
      return true;
    } else if (netType.equalsIgnoreCase("testnet")) {
      Hash.changeAddressPrefixTestnet();
      return true;
    } else {
      return false;
    }
  }

  private void initTasks(Args args) {
    logger.info("Init tasks.");

    this.taskList = new ArrayList<>();

    String stressType = args.getStressType();

    if (stressType.equalsIgnoreCase("transfer.balance")) {
      GenerateTransactionTask generateTransactionTask = new GenerateTransactionTask(
          args.getStressCount());

      SendTransactionTask sendTransactionTask = new SendTransactionTask(
          generateTransactionTask.getTransactions(),
          args.getGRpcStressAddress(),
          args.getStressTps(),
          args.isRetry());

      CheckResultTask checkResultTask = new CheckResultTask();

      this.taskList.add(generateTransactionTask);
      this.taskList.add(sendTransactionTask);
      this.taskList.add(checkResultTask);
    } else if (stressType.equalsIgnoreCase("transfer.asset")) {
      CreateAssetTask createAssetTask = new CreateAssetTask();

      GenerateTransactionTask generateTransactionTask = new GenerateTransactionTask(
          args.getStressCount());

      SendTransactionTask sendTransactionTask = new SendTransactionTask(
          generateTransactionTask.getTransactions(),
          args.getGRpcStressAddress(),
          args.getStressTps(),
          args.isRetry());

      CheckResultTask checkResultTask = new CheckResultTask();

      this.taskList.add(createAssetTask);
      this.taskList.add(generateTransactionTask);
      this.taskList.add(sendTransactionTask);
      this.taskList.add(checkResultTask);
    }
  }

  public void start() {
    logger.info("Start application.");

    for (Task task : taskList) {
      task.init(args);
      task.start();
      task.shutdown();
    }
  }
}
