package org.tron.task;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tron.common.config.Args;
import org.tron.protos.Protocol.Transaction;
import org.tron.schedule.SendSchedule;
import org.tron.service.WalletGrpcClient;

public class SendTransactionTask implements Task {

  private static final Logger logger = LoggerFactory.getLogger("SendTransactionTask");

  private static List<WalletGrpcClient> clients = new ArrayList<>();

  private ListeningExecutorService executorService;

  private ConcurrentLinkedQueue<Transaction> transactions;

  private List<String> grpcHosts;

  private int tps;

  private boolean retry;

  public SendTransactionTask(
      final ConcurrentLinkedQueue<Transaction> transactions,
      List<String> grpcHosts,
      int tps,
      boolean retry
  ) {
    logger.info("Create task: {}.", getClass().getSimpleName());

    this.transactions = transactions;
    this.grpcHosts = grpcHosts;
    this.tps = tps;
    this.retry = retry;
  }

  @Override
  public void init(Args args) {
    logger.info("Init send transaction task.");

    initGrpcClients();

    executorService = MoreExecutors
        .listeningDecorator(Executors.newFixedThreadPool(50));
  }

  private void initGrpcClients() {
    logger.info("Init gRPC clients.");

    for (String host : this.grpcHosts) {
      WalletGrpcClient client = new WalletGrpcClient(
          host);

      clients.add(client);
    }
  }

  @Override
  public void start() {
    RateLimiter limiter = RateLimiter.create(tps);
    CountDownLatch latch = new CountDownLatch(transactions.size());

    ProgressBar pb = new ProgressBar("Send transactions", transactions.size(),
        ProgressBarStyle.ASCII);

    for (int i = 0; i < transactions.size(); i++) {
      executorService
          .execute(new SendSchedule(clients.get(i % grpcHosts.size()), limiter, latch, pb,
              transactions.poll(), retry));
    }

    try {
      latch.await();

      pb.close();

      Thread.sleep(1000);

      System.out.println();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void shutdown() {

  }
}
