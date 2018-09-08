package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import com.google.protobuf.ByteString;
import com.typesafe.config.Config;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.tron.common.config.Config.ConfigProperty;
import org.tron.common.utils.Base58;
import org.tron.common.utils.TransactionUtils;
import org.tron.protos.Protocol.Account;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletGrpcClient;

public class SendTransaction {

  private static List<WalletGrpcClient> clients = new ArrayList<>();
  private static Map<Long, List<Transaction>> transactionsMap = new HashMap<>();

  @Getter
  private static ConcurrentHashMap<String, Long> startAccount = new ConcurrentHashMap<>();

  @Getter
  private static ConcurrentHashMap<String, Long> endAccount = new ConcurrentHashMap<>();

  private static SendCoinArgs argsObj;

  public static void main(String[] args) throws IOException {
    argsObj = SendCoinArgs.getInstance(args);

    List<String> grpcAddress = argsObj.getGRpcAddress();

    final int threadCount = argsObj.getThreadCount();

    double tps = argsObj.getTps();

    LongAdder count = new LongAdder();
    clients = IntStream.range(0, threadCount).mapToObj(i -> {
      WalletGrpcClient client = new WalletGrpcClient(
          grpcAddress.get(count.intValue() % grpcAddress.size()));
      count.increment();
      return client;
    }).collect(Collectors.toList());

    File f = new File(argsObj.getDataFile());
    FileInputStream fis = new FileInputStream(f);

    Transaction transaction;
    long trxCount = 0;
    while ((transaction = Transaction.parseDelimitedFrom(fis)) != null) {
      transactionsMap.computeIfAbsent(trxCount % threadCount, k -> new ArrayList<>())
          .add(transaction);
      trxCount++;
    }

    setStartAccountMap(clients.get(0));
    rateLimiter(threadCount, tps);
  }

  private static void setStartAccountMap(WalletGrpcClient client) {
    List<String> accountAddressList = argsObj.getAccountAddress();
    accountAddressList.forEach(a -> {
      startAccount.put(a,
          client.getAccount(Account.newBuilder().setAddress(ByteString.copyFrom(Objects
              .requireNonNull(Base58.decodeFromBase58Check(a)))).build()).getBalance());
    });
  }

  public static void setEndAccountMap(WalletGrpcClient client) {
    List<String> accountAddressList = argsObj.getAccountAddress();
    accountAddressList.forEach(a -> {
      endAccount.put(a,
          client.getAccount(Account.newBuilder().setAddress(ByteString.copyFrom(Objects
              .requireNonNull(Base58.decodeFromBase58Check(a)))).build()).getBalance());
    });
  }

  public static void rateLimiter(int threadCount, double tps) {
    ListeningExecutorService executorService = MoreExecutors
        .listeningDecorator(Executors.newFixedThreadPool(threadCount));
    CountDownLatch latch = new CountDownLatch(threadCount);
    RateLimiter limiter = RateLimiter.create(tps);

    for (int i = 0; i < threadCount; ++i) {
      executorService.execute(new Task(clients.get(i % threadCount), limiter,
          transactionsMap.get((i % threadCount * 1L)), threadCount));
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

  public static class Task implements Runnable {

    private static Map<String, Boolean> successTransactionID = new ConcurrentHashMap<>();

    private static LongAdder trueCount = new LongAdder();
    private static LongAdder falseCount = new LongAdder();
    private static LongAdder currentCount = new LongAdder();
    private static ConcurrentHashMap<Long, LongAdder> resultMap = new ConcurrentHashMap<>();
    public static final ScheduledExecutorService service = Executors
        .newSingleThreadScheduledExecutor();
    private WalletGrpcClient client;
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
                + ", success: " + trueCount.longValue()
                + ", failed: " + falseCount.longValue()
                + ", timestamp: " + (System.currentTimeMillis() / 1000)
                + ", map: " + resultMap);

        if (endCounts.longValue() == threadCount) {
          endTime = new Date();
          System.out.printf(
              "total:%d,success:%d,failed:%d,seconds:%d\n",
              currentCount.longValue(),
              trueCount.longValue(),
              falseCount.longValue(),
              ((endTime.getTime() - startTime.getTime()) / 1000));

          // save successTransactionID for check by getTransactionInfoById
          BufferedWriter bufferedWriter = null;
          try {
            bufferedWriter = new BufferedWriter(
                new FileWriter("transactionsID.csv"));

            for (String key : successTransactionID.keySet()) {
              bufferedWriter.write(key);
              bufferedWriter.newLine();
            }

          } catch (IOException e) {
            e.printStackTrace();
          } finally {
            if (bufferedWriter != null) {
              try {
                bufferedWriter.close();
              } catch (IOException e) {
                e.printStackTrace();
              }
            }
          }

          service.shutdown();
        }
      }, 5, 5, TimeUnit.SECONDS);
    }

    public Task(final WalletGrpcClient client, RateLimiter limiter,
        List<Transaction> transactions, int threadCount) {
      this.client = client;
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
          int b;
          do {
            b = client.broadcastTransactionRetry(t);

            if (b == 0) {
              trueCount.increment();
              successTransactionID.put(TransactionUtils.getID(t).toString(), true);
            } else if (b == 2) {
              falseCount.increment();
            }

            currentCount.increment();

            long currentMinutes = System.currentTimeMillis() / 1000L / 60;

            resultMap.computeIfAbsent(currentMinutes, k -> new LongAdder()).increment();
          } while (argsObj.isRetry() && (b == 1));

        });
      }
      try {
        this.client.shutdown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      this.endCounts.increment();
    }
  }

  public static class SendCoinArgs {

    private static final String DEFAULT_CONFIG_FILE_PATH = "config_send_coin_loop.conf";
    private static final String GRPC_ADDRESS = "grpc.address";
    private static final String THREAD_COUNT = "thread.count";
    private static final String DATA_FILE = "data.file";
    private static final String TPS = "tps";
    private static final String ACCOUNT_ADDRESS = "account.address";
    private static final String RETRY = "retry";

    private static SendCoinArgs INSTANCE;

    @Getter
    @Parameter(names = {"--config"}, description = "Config file path")
    private String config = "";

    @Getter
    @Parameter(names = {"--gRpcAddress"}, description = "gRPC address, like: 127.0.0.1:50051")
    private List<String> gRpcAddress = new ArrayList<>();

    @Getter
    @Parameter(names = {"--threadCount"}, description = "Thread count")
    private int threadCount = 0;

    @Getter
    @Parameter(names = {
        "--dataFile"}, description = "Data file")
    private String dataFile = "";

    @Getter
    @Parameter(names = {
        "--tps"}, description = "tps")
    private int tps = 0;

    @Getter
    @Parameter(names = {"--accountAddress"}, description = "Get account address list")
    private List<String> accountAddress = new ArrayList<>();

    @Getter
    @Parameter(names = {"--retry"}, description = "Retry")
    private boolean retry = false;

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
      EnumMap<ConfigProperty, Object> configInfo = configMap
          .getConfig(configFilePath, DEFAULT_CONFIG_FILE_PATH);

      Config config = (Config) configInfo.get(ConfigProperty.CONFIG);
      String configTip = (String) configInfo.get(ConfigProperty.TIP);

      System.out.printf("Loading config file: \u001B[34m%s\u001B[0m", configTip);
      System.out.println();

      if (CollectionUtils.isEmpty(INSTANCE.gRpcAddress)) {
        INSTANCE.gRpcAddress = config.getStringList(GRPC_ADDRESS);
      }

      System.out.printf("gRPC address: \u001B[34m%s\u001B[0m", INSTANCE.gRpcAddress);
      System.out.println();

      if (0 == INSTANCE.threadCount) {
        INSTANCE.threadCount = config.getInt(THREAD_COUNT);
      }

      System.out.printf("Thread count: \u001B[34m%s\u001B[0m", INSTANCE.threadCount);
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

      if (0 == INSTANCE.accountAddress.size()) {
        INSTANCE.accountAddress = config.getStringList(ACCOUNT_ADDRESS);
      }

      System.out.printf("Account address: \u001B[34m%s\u001B[0m", INSTANCE.accountAddress);
      System.out.println();

      if (false == INSTANCE.retry) {
        if (config.hasPath(RETRY)) {
          INSTANCE.retry = config.getBoolean(RETRY);
        }
      }

      System.out.printf("Retry: \u001B[34m%s\u001B[0m", INSTANCE.retry);
      System.out.println();
    }
  }
}