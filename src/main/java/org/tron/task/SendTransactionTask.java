package org.tron.task;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.tron.common.config.Args;
import org.tron.protos.Protocol.Transaction;
import org.tron.schedule.SendSchedule;
import org.tron.service.WalletGrpcClient;

@Slf4j
public class SendTransactionTask implements Task {

  private List<WalletGrpcClient> clients = new ArrayList<>();

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
        .listeningDecorator(Executors.newFixedThreadPool(100));
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
    logger.info("Start send transaction task.");

    RateLimiter limiter = RateLimiter.create(tps);
    CountDownLatch latch = new CountDownLatch(transactions.size());

    ProgressBar pb = new ProgressBar("Send transactions", transactions.size(),
        ProgressBarStyle.ASCII);

    int i = 0;
    while (transactions.size() > 0) {
      executorService
          .execute(new SendSchedule(clients.get(i % grpcHosts.size()), limiter, latch, pb,
              transactions.poll(), retry));

      i++;
    }

    try {
      latch.await();

      pb.close();

      Thread.sleep(1000);

      System.out.println();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      executorService.shutdown();

      while (true) {
        if (executorService.isTerminated()) {
          break;
        }

        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void shutdown() {
    logger.info("Shutdown send transaction task.");

    for (WalletGrpcClient client :
        clients) {
      try {
        client.shutdown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
