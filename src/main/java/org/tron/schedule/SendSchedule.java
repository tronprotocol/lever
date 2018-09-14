package org.tron.schedule;

import com.google.common.util.concurrent.RateLimiter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import me.tongfei.progressbar.ProgressBar;
import org.tron.api.GrpcAPI.Return.response_code;
import org.tron.common.utils.TransactionUtils;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletGrpcClient;

public class SendSchedule implements Runnable {

  private static volatile Map<String, Boolean> successTransactionID = new ConcurrentHashMap<>();
  private static volatile Map<String, AtomicInteger> statistics = new ConcurrentHashMap<>();

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
      boolean canRetry;
      do {
        canRetry = false;
        response_code code = client.broadcastTransactionRetry(transaction);

        String statisticsKey;
        switch (code) {
          case SUCCESS:
            successTransactionID.put(TransactionUtils.getID(transaction).toString(), true);
            statisticsKey = "success";

            break;
          case SIGERROR:
            statisticsKey = "sigerror";

            break;
          case CONTRACT_VALIDATE_ERROR:
            statisticsKey = "contract_validate_error";

            break;
          case CONTRACT_EXE_ERROR:
            statisticsKey = "contract_exe_error";

            break;
          case BANDWITH_ERROR:
            statisticsKey = "bandwith_error";

            break;
          case DUP_TRANSACTION_ERROR:
            statisticsKey = "dup_transaction_error";

            break;
          case TAPOS_ERROR:
            statisticsKey = "tapos_error";

            break;
          case TOO_BIG_TRANSACTION_ERROR:
            statisticsKey = "too_big_transaction_error";

            break;
          case TRANSACTION_EXPIRATION_ERROR:
            statisticsKey = "transaction_expiration_error";

            break;
          case SERVER_BUSY:
            canRetry = true;

            statisticsKey = "server_busy";

            break;
          case OTHER_ERROR:
          default:
            statisticsKey = "other_error";

            break;
        }

        synchronized (statistics) {
          AtomicInteger atomicInteger = statistics.get(statisticsKey);

          if (null == atomicInteger) {
            statistics.put(statisticsKey, new AtomicInteger(1));
          } else {
            atomicInteger.incrementAndGet();
          }
        }

      } while (retry && (canRetry));
    }
    synchronized (this) {
      pb.step();
    }
    latch.countDown();
  }

  public static Map<String, AtomicInteger> getStatistics() {
    return statistics;
  }

  public static Map<String, Boolean> getSuccessTransactionID() {
    return successTransactionID;
  }
}
