package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import lombok.Getter;
import org.tron.service.WalletClient;

public class ListWitness {

  private static WalletClient walletClient;

  public static void main(String[] args) {
    ListWitnessArgs argsObj = new ListWitnessArgs();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    String grpcAddress = argsObj.getGRpcAddress();

    double tps = argsObj.getTps();
    long duration = argsObj.getDuration();

    walletClient = new WalletClient();
    walletClient.init(grpcAddress);

    rateLimiter(tps, duration);
  }

  public static void rateLimiter(double tps, long duration) {
    ListeningExecutorService executorService = MoreExecutors
        .listeningDecorator(Executors.newFixedThreadPool(8));
    CountDownLatch latch = new CountDownLatch(8);
    RateLimiter limiter = RateLimiter.create(tps);

    for (int i = 0; i < 8; ++i) {
      executorService.execute(new ListWitnessTask(walletClient, limiter, duration));
      latch.countDown();
    }

    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      executorService.shutdown();
    }
  }
}

class ListWitnessTask implements Runnable {

  private static LongAdder trueCount = new LongAdder();
  private static LongAdder currentCount = new LongAdder();
  private static ConcurrentHashMap<Long, LongAdder> resultMap = new ConcurrentHashMap<>();
  public static final ScheduledExecutorService service = Executors
      .newSingleThreadScheduledExecutor();
  private WalletClient walletClient;
  private RateLimiter limiter;
  private static LongAdder endCounts = new LongAdder();

  private static Date startTime;
  private static Date endTime;
  private static long duration;

  static {
    service.scheduleAtFixedRate(() -> {
      System.out.println(
          "current: " + currentCount.longValue()
              + ", true: " + trueCount.longValue()
              + ", timestamp: " + (System.currentTimeMillis() / 1000)
              + ", map: " + resultMap);

      if (endCounts.longValue() == 8) {
        endTime = new Date();
        System.out.printf(
            "\u001B[36mstart time:\u001B[0m %tF %tT, \u001B[36mend time:\u001B[0m %tF %tT\n",
            startTime, startTime, endTime,
            endTime);

        service.shutdown();
      }
    }, 5, 5, TimeUnit.SECONDS);
  }

  public ListWitnessTask(final WalletClient walletClient, RateLimiter limiter, long d) {
    this.walletClient = walletClient;
    this.limiter = limiter;
    duration = d;
  }

  @Override
  public void run() {
    long s = System.currentTimeMillis() / 1000;
    long e;
    do {
      if (endCounts.longValue() == 0) {
        startTime = new Date();
      }

      limiter.acquire();
      walletClient.listWitness();

      trueCount.increment();

      currentCount.increment();

      long currentMinutes = System.currentTimeMillis() / 1000L / 60;

      resultMap.computeIfAbsent(currentMinutes, k -> new LongAdder()).increment();

      e = System.currentTimeMillis() / 1000;
    } while ((e - s) <= duration);

    this.walletClient.shutdown();
    this.endCounts.increment();
  }
}

class ListWitnessArgs {
  @Getter
  @Parameter(names = {"--gRpcAddress"}, required = true, description = "gRPC address, like: 127.0.0.1:50051")
  private String gRpcAddress = "";

  @Getter
  @Parameter(names = {
      "--tps"}, description = "tps", required = true)
  private int tps = 0;

  @Getter
  @Parameter(names = {
      "--duration"}, description = "duration", required = true)
  private long duration = 0L;
}