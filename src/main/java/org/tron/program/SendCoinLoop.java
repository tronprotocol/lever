package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import org.tron.Validator.LongValidator;
import org.tron.Validator.StringValidator;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletClient;

public class SendCoinLoop {

  private static final int THREAD_COUNT = 32;

  private static List<WalletClient> walletClients = new ArrayList<>();
  private static Map<Long, List<Transaction>> transactionsMap = new HashMap<>();

  public static void main(String[] args) throws IOException {
    SendCoinArgs args1 = new SendCoinArgs();
    JCommander.newBuilder().addObject(args1).build().parse(args);

    double tps = args1.getTps();

    walletClients = IntStream.range(0, THREAD_COUNT).mapToObj(i -> {
      WalletClient walletClient = new WalletClient();
      walletClient.init();
      return walletClient;
    })
        .collect(Collectors.toList());

    File f = new File(args1.getDataFile());
    FileInputStream fis = new FileInputStream(f);

    Transaction transaction;
    long trxCount = 0;
    while ((transaction = Transaction.parseDelimitedFrom(fis)) != null) {
      transactionsMap.computeIfAbsent(trxCount % THREAD_COUNT, k -> new ArrayList<>())
          .add(transaction);
      trxCount++;
    }

    rateLimiter(tps);
  }

  public static void rateLimiter(double tps) {
    ListeningExecutorService executorService = MoreExecutors
        .listeningDecorator(Executors.newFixedThreadPool(THREAD_COUNT));
    CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
    RateLimiter limiter = RateLimiter.create(tps);

    for (int i = 0; i < THREAD_COUNT; ++i) {
      executorService.execute(new Task(walletClients.get(i % THREAD_COUNT), limiter,
          transactionsMap.get((i % THREAD_COUNT * 1L)), THREAD_COUNT));
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

class Task implements Runnable {

  private static LongAdder trueCount = new LongAdder();
  private static LongAdder falseCount = new LongAdder();
  private static LongAdder currentCount = new LongAdder();
  private static ConcurrentHashMap<Long, LongAdder> resultMap = new ConcurrentHashMap<>();
  public static final ScheduledExecutorService service = Executors
      .newSingleThreadScheduledExecutor();
  private WalletClient walletClient;
  private RateLimiter limiter;
  private List<Transaction> transactions;
  private static LongAdder endCounts = new LongAdder();
  private static int threadCount;

  static {
    service.scheduleAtFixedRate(() -> {
      System.out.println(
          "current: " + currentCount.longValue()
              + ", true: " + trueCount.longValue()
              + ", false: " + falseCount.longValue()
              + ", timestamp: " + (System.currentTimeMillis() / 1000)
              + ", map: " + resultMap);

      if (endCounts.longValue() == threadCount) {
        service.shutdown();
      }
    }, 5, 5, TimeUnit.SECONDS);
  }

  public Task(final WalletClient walletClient, RateLimiter limiter,
      List<Transaction> transactions, int threadCount) {
    this.walletClient = walletClient;
    this.limiter = limiter;
    this.transactions = transactions;
    this.threadCount = threadCount;
  }

  @Override
  public void run() {
    if (this.transactions != null) {
      this.transactions.forEach(t -> {
        limiter.acquire();
        boolean b = walletClient.broadcastTransaction(t);

        if (b) {
          trueCount.increment();
        } else {
          falseCount.increment();
        }

        currentCount.increment();

        long currentMinutes = System.currentTimeMillis() / 1000L / 60;

        resultMap.computeIfAbsent(currentMinutes, k -> new LongAdder()).increment();
      });
    }
    this.endCounts.increment();
  }
}

class SendCoinArgs {

  @Getter
  @Parameter(names = {
      "--datafile"}, description = "Data file", required = true, validateWith = StringValidator.class)
  private String dataFile;

  @Getter
  @Parameter(names = {
      "--tps"}, description = "tps", required = true, validateWith = LongValidator.class)
  private double tps;
}