package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import com.typesafe.config.Config;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
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
import org.apache.commons.lang3.StringUtils;
import org.tron.common.config.Config.ConfigProperty;
import org.tron.common.utils.Time;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletClient;

public class SendCoinLoop {

  private static final int THREAD_COUNT = 16;

  private static List<WalletClient> walletClients = new ArrayList<>();
  private static Map<Long, List<Transaction>> transactionsMap = new HashMap<>();

  public static void main(String[] args) throws IOException {
    SendCoinArgs argsObj = SendCoinArgs.getInstance(args);

    double tps = argsObj.getTps();

    walletClients = IntStream.range(0, THREAD_COUNT).mapToObj(i -> {
      WalletClient walletClient = new WalletClient();
      walletClient.init(argsObj.getGRpcAddress());
      return walletClient;
    }).collect(Collectors.toList());

    File f = new File(argsObj.getDataFile());
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

  private static Date startTime;
  private static Date endTime;

  static {
    service.scheduleAtFixedRate(() -> {
      System.out.println(
          "current: " + currentCount.longValue()
              + ", true: " + trueCount.longValue()
              + ", false: " + falseCount.longValue()
              + ", timestamp: " + (System.currentTimeMillis() / 1000)
              + ", map: " + resultMap);

      if (endCounts.longValue() == threadCount) {
        endTime = new Date();
        System.out.printf("start time: %tF %tT, end time: %tF %tT", startTime, startTime, endTime, endTime);
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
      if (endCounts.longValue() == 0) {
        startTime = new Date();
      }

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
    this.walletClient.shutdown();
    this.endCounts.increment();
  }
}

class SendCoinArgs {

  private static final String DEFAULT_CONFIG_FILE_PATH = "config_send_coin_loop.conf";
  private static final String GRPC_ADDRESS = "grpc.address";
  private static final String DATA_FILE = "data.file";
  private static final String TPS = "tps";

  private static SendCoinArgs INSTANCE;

  @Getter
  @Parameter(names = {"--config"}, description = "Config file path")
  private String config = "";

  @Getter
  @Parameter(names = {"--gRpcAddress"}, description = "gRPC address, like: 127.0.0.1:50051")
  private String gRpcAddress = "";

  @Getter
  @Parameter(names = {
      "--dataFile"}, description = "Data file")
  private String dataFile = "";

  @Getter
  @Parameter(names = {
      "--tps"}, description = "tps")
  private int tps = 0;

  private SendCoinArgs() {

  }

  public static SendCoinArgs getInstance(String[] args) {
    if (null == INSTANCE) {
      INSTANCE = new SendCoinArgs();
      JCommander.newBuilder().addObject(INSTANCE).build().parse(args);
      INSTANCE.initArgs();
    }

    return INSTANCE;
  }

  private void initArgs() {
    String configFilePath = INSTANCE.config;
    if (StringUtils.isBlank(configFilePath)) {
      configFilePath = DEFAULT_CONFIG_FILE_PATH;
    }

    org.tron.common.config.Config configMap = new org.tron.common.config.ConfigImpl();
    EnumMap<ConfigProperty, Object> configInfo = configMap.getConfig(configFilePath, DEFAULT_CONFIG_FILE_PATH);

    Config config = (Config) configInfo.get(ConfigProperty.CONFIG);
    String configTip = (String) configInfo.get(ConfigProperty.TIP);

    System.out.printf("Loading config file: \u001B[34m%s\u001B[0m", configTip);
    System.out.println();

    if (StringUtils.isBlank(INSTANCE.gRpcAddress)) {
      INSTANCE.gRpcAddress = config.getString(GRPC_ADDRESS);
    }

    System.out.printf("gRPC address: \u001B[34m%s\u001B[0m", INSTANCE.gRpcAddress);
    System.out.println();

    if (StringUtils.isBlank(INSTANCE.dataFile)) {
      INSTANCE.dataFile = config.getString(DATA_FILE);
    }

    System.out.printf("Data file: \u001B[34m%s\u001B[0m", INSTANCE.dataFile);
    System.out.println();

    if (0 == INSTANCE.tps) {
      INSTANCE.tps = config.getInt(TPS);
    }

    System.out.printf("TPS: \u001B[34m%s\u001B[0m", INSTANCE.tps);
    System.out.println();
  }
}