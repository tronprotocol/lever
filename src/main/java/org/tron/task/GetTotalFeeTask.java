package org.tron.task;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.tron.common.config.Args;
import org.tron.protos.Protocol.TransactionInfo;
import org.tron.schedule.SendSchedule;
import org.tron.service.WalletGrpcClient;

@Slf4j
public class GetTotalFeeTask implements Task {

  private Map<String, Boolean> successTransactionID;
  private String grpcHost;
  private WalletGrpcClient client;
  private long totalFee;

  public GetTotalFeeTask() {
    logger.info("Create task: {}.", getClass().getSimpleName());
  }

  @Override
  public void init(Args args) {
    logger.info("Init get total fee task.");

    grpcHost = args.getGRpcCheckAddress().get(0);
    successTransactionID = SendSchedule.getSuccessTransactionID();
    initGrpcClients();
  }

  private void initGrpcClients() {
    logger.info("Init gRPC clients.");

    client = new WalletGrpcClient(grpcHost);
  }

  @Override
  public void start() {
    logger.info("Start get total fee task.");

    Set<Entry<String, Boolean>> entries = successTransactionID.entrySet();
    for (Entry<String, Boolean> entry : entries) {
      Optional<TransactionInfo> transactionInfo = client.getTransactionInfoById(entry.getKey());

      if (transactionInfo.isPresent()) {
        totalFee += transactionInfo.get().getFee();
      }
    }
  }

  @Override
  public void shutdown() {
    logger.info("Shutdown gRPC clients.");
    try {
      client.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public long getTotalFee() {
    return totalFee;
  }
}
