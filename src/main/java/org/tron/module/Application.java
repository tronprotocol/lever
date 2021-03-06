package org.tron.module;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.tron.check.CheckContractResult;
import org.tron.check.CheckTransferAssetResult;
import org.tron.check.CheckTransferBalanceResult;

import org.tron.common.config.Args;
import org.tron.common.crypto.Hash;
import org.tron.task.CheckCoinResultTask;
import org.tron.task.CheckResultTask;
import org.tron.task.CheckStableTransaction;
import org.tron.task.CreateAssetTask;
import org.tron.task.DeployAirContractTask;
import org.tron.task.DeployContractTask;
import org.tron.task.GenerateTransactionTask;
import org.tron.task.GetAccountTask;
import org.tron.task.GetTotalFeeTask;
import org.tron.task.GetVMResultTask;
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
      CheckStableTransaction checkStableTransaction1 = new CheckStableTransaction(
          args.getGRpcCheckAddress());

      GetAccountTask getStartOwnerAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckOwnerAccountAddress());

      GetAccountTask getStartToAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckToAccountAddress());

      GenerateTransactionTask generateTransactionTask = new GenerateTransactionTask(
          args.getStressCount());

      SendTransactionTask sendTransactionTask = new SendTransactionTask(
          generateTransactionTask.getTransactions(),
          args.getGRpcStressAddress(),
          args.getStressTps(),
          args.isRetry());

      CheckStableTransaction checkStableTransaction2 = new CheckStableTransaction(
          args.getGRpcCheckAddress());

      GetAccountTask getEndOwnerAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckOwnerAccountAddress());
      GetAccountTask getEndToAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckToAccountAddress());

      GetTotalFeeTask getTotalFeeTask = new GetTotalFeeTask();

      CheckResultTask checkResultTask = new CheckResultTask(new CheckTransferBalanceResult(),
          getStartOwnerAccountTask, getStartToAccountTask, getEndOwnerAccountTask,
          getEndToAccountTask, getTotalFeeTask);

      this.taskList.add(checkStableTransaction1);
      this.taskList.add(getStartOwnerAccountTask);
      this.taskList.add(getStartToAccountTask);
      this.taskList.add(generateTransactionTask);
      this.taskList.add(sendTransactionTask);
      this.taskList.add(checkStableTransaction2);
      this.taskList.add(getEndOwnerAccountTask);
      this.taskList.add(getEndToAccountTask);
      this.taskList.add(getTotalFeeTask);
      this.taskList.add(checkResultTask);
    } else if (stressType.equalsIgnoreCase("transfer.asset")) {
      // TODO
      CheckStableTransaction checkStableTransaction1 = new CheckStableTransaction(
          args.getGRpcCheckAddress());

      // TODO: create asset
      CreateAssetTask getCreateAssetTask = new CreateAssetTask();

      GetAccountTask getStartOwnerAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckOwnerAccountAddress());
      GetAccountTask getStartToAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckToAccountAddress());

      GenerateTransactionTask generateTransactionTask = new GenerateTransactionTask(
          args.getStressCount());
      SendTransactionTask sendTransactionTask = new SendTransactionTask(
          generateTransactionTask.getTransactions(),
          args.getGRpcStressAddress(),
          args.getStressTps(),
          args.isRetry());
      CheckStableTransaction checkStableTransaction2 = new CheckStableTransaction(
          args.getGRpcCheckAddress());
      GetAccountTask getEndOwnerAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckOwnerAccountAddress());
      GetAccountTask getEndToAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckToAccountAddress());

      GetTotalFeeTask getTotalFeeTask = new GetTotalFeeTask();

      CheckResultTask checkResultTask = new CheckResultTask(new CheckTransferAssetResult(),
          getStartOwnerAccountTask, getStartToAccountTask, getEndOwnerAccountTask,
          getEndToAccountTask, getTotalFeeTask);

      this.taskList.add(checkStableTransaction1);
      // TODO: create asset
      this.taskList.add(getCreateAssetTask);
      this.taskList.add(getStartOwnerAccountTask);
      this.taskList.add(getStartToAccountTask);
      this.taskList.add(generateTransactionTask);
      this.taskList.add(sendTransactionTask);
      this.taskList.add(checkStableTransaction2);
      this.taskList.add(getEndOwnerAccountTask);
      this.taskList.add(getEndToAccountTask);
      this.taskList.add(getTotalFeeTask);
      this.taskList.add(checkResultTask);
    } else if (stressType.equalsIgnoreCase("transfer.deploy.contract")) {
      // TODO
      CheckStableTransaction checkStableTransaction1 = new CheckStableTransaction(
          args.getGRpcCheckAddress());

      GetAccountTask getStartOwnerAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckOwnerAccountAddress());
      GetAccountTask getStartToAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckToAccountAddress());

      GenerateTransactionTask generateTransactionTask = new GenerateTransactionTask(
          args.getStressCount());

      SendTransactionTask sendTransactionTask = new SendTransactionTask(
          generateTransactionTask.getTransactions(),
          args.getGRpcStressAddress(),
          args.getStressTps(),
          args.isRetry());

      CheckStableTransaction checkStableTransaction2 = new CheckStableTransaction(
          args.getGRpcCheckAddress());

      GetAccountTask getEndOwnerAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckOwnerAccountAddress());
      GetAccountTask getEndToAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckToAccountAddress());

      GetTotalFeeTask getTotalFeeTask = new GetTotalFeeTask();

      CheckResultTask checkResultTask = new CheckResultTask(new CheckContractResult(),
          getStartOwnerAccountTask, getStartToAccountTask, getEndOwnerAccountTask,
          getEndToAccountTask, getTotalFeeTask);

      this.taskList.add(checkStableTransaction1);
      this.taskList.add(getStartOwnerAccountTask);
      this.taskList.add(generateTransactionTask);
      this.taskList.add(sendTransactionTask);
      this.taskList.add(checkStableTransaction2);
      this.taskList.add(getEndOwnerAccountTask);
      this.taskList.add(getTotalFeeTask);
      this.taskList.add(checkResultTask);
    } else if (stressType.equalsIgnoreCase("transfer.trigger.contract")) {
      // TODO
      CheckStableTransaction checkStableTransaction1 = new CheckStableTransaction(
          args.getGRpcCheckAddress());
      // TODO: 部署一个合约
      DeployContractTask deployContractTask = new DeployContractTask();

      GetAccountTask getStartOwnerAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckOwnerAccountAddress());
      GetAccountTask getStartToAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckToAccountAddress());
      GenerateTransactionTask generateTransactionTask = new GenerateTransactionTask(
          args.getStressCount());
      SendTransactionTask sendTransactionTask = new SendTransactionTask(
          generateTransactionTask.getTransactions(),
          args.getGRpcStressAddress(),
          args.getStressTps(),
          args.isRetry());
      CheckStableTransaction checkStableTransaction2 = new CheckStableTransaction(
          args.getGRpcCheckAddress());
      GetAccountTask getEndOwnerAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckOwnerAccountAddress());
      GetAccountTask getEndToAccountTask = new GetAccountTask(args.getGRpcCheckAddress(),
          args.getCheckToAccountAddress());
      GetTotalFeeTask getTotalFeeTask = new GetTotalFeeTask();
      CheckResultTask checkResultTask = new CheckResultTask(new CheckContractResult(),
          getStartOwnerAccountTask, getStartToAccountTask, getEndOwnerAccountTask,
          getEndToAccountTask, getTotalFeeTask);

      this.taskList.add(checkStableTransaction1);
      // TODO: 部署一个合约
      this.taskList.add(deployContractTask);
      this.taskList.add(getStartOwnerAccountTask);
      this.taskList.add(generateTransactionTask);
      this.taskList.add(sendTransactionTask);
      this.taskList.add(checkStableTransaction2);
      this.taskList.add(getEndOwnerAccountTask);
      this.taskList.add(getTotalFeeTask);
      this.taskList.add(checkResultTask);
    } else if (stressType.equalsIgnoreCase("transfer.airdrop")) {
      // TODO
      CheckStableTransaction checkStableTransaction1 = new CheckStableTransaction(
          args.getGRpcCheckAddress());

      DeployAirContractTask getAirContractTask = new DeployAirContractTask();


      GetVMResultTask getStartOwnerVMResultTask = new GetVMResultTask(args.getGRpcCheckAddress(),
          args.getCheckOwnerAccountAddress());

      GetVMResultTask getStartToVMResultTask = new GetVMResultTask(args.getGRpcCheckAddress(),
          args.getCheckToAccountAddress());

      GenerateTransactionTask generateTransactionTask = new GenerateTransactionTask(
          args.getStressCount());
      SendTransactionTask sendTransactionTask = new SendTransactionTask(
          generateTransactionTask.getTransactions(),
          args.getGRpcStressAddress(),
          args.getStressTps(),
          args.isRetry());
      CheckStableTransaction checkStableTransaction2 = new CheckStableTransaction(
          args.getGRpcCheckAddress());

      GetVMResultTask getEndOwnerVMResultTask = new GetVMResultTask(args.getGRpcCheckAddress(),
          args.getCheckOwnerAccountAddress());

      GetVMResultTask getEndToVMResultTask = new GetVMResultTask(args.getGRpcCheckAddress(),
          args.getCheckToAccountAddress());

      GetTotalFeeTask getTotalFeeTask = new GetTotalFeeTask();
      CheckCoinResultTask checkResultTask = new CheckCoinResultTask(new CheckContractResult(),
          getStartOwnerVMResultTask, getStartToVMResultTask, getEndOwnerVMResultTask,
          getEndToVMResultTask, getTotalFeeTask);

      this.taskList.add(checkStableTransaction1);
      // TODO: 部署一个代币合约
      this.taskList.add(getAirContractTask);
      this.taskList.add(getStartOwnerVMResultTask);
      this.taskList.add(generateTransactionTask);
      this.taskList.add(sendTransactionTask);
      this.taskList.add(checkStableTransaction2);
      this.taskList.add(getEndOwnerVMResultTask);
      this.taskList.add(getTotalFeeTask);
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
