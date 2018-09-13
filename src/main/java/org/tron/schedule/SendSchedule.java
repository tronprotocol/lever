package org.tron.schedule;

import com.google.common.util.concurrent.RateLimiter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import me.tongfei.progressbar.ProgressBar;
import org.tron.common.utils.TransactionUtils;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletGrpcClient;

public class SendSchedule implements Runnable {

  private static Map<String, Boolean> successTransactionID = new ConcurrentHashMap<>();

  public static final ScheduledExecutorService service = Executors
      .newSingleThreadScheduledExecutor();
  private WalletGrpcClient client;
  private RateLimiter limiter;
  private CountDownLatch latch;
  private ProgressBar pb;
  private Transaction transaction;
  private boolean retry;

  public SendSchedule(final WalletGrpcClient client, RateLimiter limiter, CountDownLatch latch,
      ProgressBar pb,
      Transaction transaction, boolean retry) {
    this.client = client;
    this.limiter = limiter;
    this.latch = latch;
    this.pb = pb;
    this.transaction = transaction;
    this.retry = retry;
  }

  @Override
  public void run() {
    if (this.transaction != null) {
      limiter.acquire();
      int b;
      do {
        b = client.broadcastTransactionRetry(transaction);

        if (b == 0) {
          successTransactionID.put(TransactionUtils.getID(transaction).toString(), true);
        }

      } while (retry && (b == 1));
    }
    pb.step();
    latch.countDown();
  }
}
