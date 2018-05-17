package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

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
import org.tron.common.utils.Base58;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletClient;


//Example --tps 2000 --datafile [path to trxsdata.csv]
public class SendCoinLoop {
  private static final int THREAD_COUNT = 1;

  private static List<WalletClient> walletClients = new ArrayList<>();
  private static Map<Long, List<Transaction>> transactionsMap = new HashMap<>();

  public static void main(String[] args) throws IOException {
    SendCoinArgs args1 = new SendCoinArgs();
    JCommander.newBuilder().addObject(args1).build().parse(args);

    double tps = args1.getTps();
    tps = 10;

    walletClients = IntStream.range(0, THREAD_COUNT).mapToObj(i -> {
      WalletClient walletClient = new WalletClient("06BCCD3C89BC855368152FFBE4829502E6482D69783E206DA6529E5849B2313F");
      walletClient.init(i);
      return walletClient;
    }).collect(Collectors.toList());

//    File f = new File(args1.getDataFile());
//    FileInputStream fis = new FileInputStream(f);

    Transaction transaction;
    long trxCount = 0;
//    while ((transaction = Transaction.parseDelimitedFrom(fis)) != null) {
//      transactionsMap.computeIfAbsent(trxCount % THREAD_COUNT, k -> new ArrayList<>())
//          .add(transaction);
//      trxCount++;
//    }

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
  private static final long ONE_MINUTES = 60 * 1000L;
  private static final long ONE_HOURS = 60 * ONE_MINUTES;
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
  private static long count = 1;
  private static LongAdder succCount = new LongAdder();
  private byte[] to = Base58.decodeFromBase58Check("27aPsDdjVtYuzMTRF4jCL6zgw2kPhsThtz4");
  private long start = System.currentTimeMillis();
  private static List<Long> falseTrxs = new ArrayList<>();
  static {
    service.scheduleAtFixedRate(() -> {
      System.out.println(
          "current: " + currentCount.longValue()
              + ", true: " + trueCount.longValue()
              + ", false: " + falseCount.longValue()
              + ", falseTrxs:" + falseTrxs
              + ", timestamp: " + (System.currentTimeMillis() / 1000L)
//              + ", map: " + resultMap
              + ", total count: " + succCount.longValue()
      );

//      if (endCounts.longValue() == threadCount) {
//        service.shutdown();
//      }
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
      while (System.currentTimeMillis() - start <= 5 * ONE_MINUTES) {
//      for (int i=0; i< 1; i++) {
        limiter.acquire();
        boolean b = walletClient.sendCoin(to, count * 1000_000L);
//        count++;

        if (b) {
          trueCount.increment();
          succCount.add(count);
        } else {
          falseCount.increment();
          falseTrxs.add(count);
        }

        currentCount.increment();

//        long currentMinutes = System.currentTimeMillis() / 1000L / 60;
        count = count % 30 + 1;

//        resultMap.computeIfAbsent(currentMinutes, k -> new LongAdder()).increment();
      }
//      this.endCounts.increment();
    }
}

class SendCoinArgs {

  @Getter
  @Parameter(names = {
      "--datafile"}, description = "Data file", required = false, validateWith = StringValidator.class)
  private String dataFile;

  @Getter
  @Parameter(names = {
      "--tps"}, description = "tps", required = false, validateWith = LongValidator.class)
  private double tps;
}