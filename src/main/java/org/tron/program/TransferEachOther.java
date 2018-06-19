package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import com.typesafe.config.Config;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.tron.Validator.StringValidator;
import org.tron.common.config.Config.ConfigProperty;
import org.tron.common.crypto.Hash;
import org.tron.common.utils.Base58;
import org.tron.core.config.Parameter.CommonConstant;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletClient;

public class TransferEachOther {

  private static final int THREAD_COUNT = 10;

  private static List<WalletClient> walletClients = new ArrayList<>();

  private static TransferEachOtherArgs argsObj;

  public static void main(String[] args) {
    argsObj = TransferEachOtherArgs.getInstance(args);

    if (CommonConstant.NET_TYPE_MAINNET.equals(argsObj.getNetType())) {
      Hash.changeAddressPrefixMainnet();
    } else {
      Hash.changeAddressPrefixTestnet();
    }

    String grpcAddress = argsObj.getGRpcAddress();

    double tps = argsObj.getTps();

    String accountAddress = argsObj.getAccountAddress();
    String accountPrivateKey = argsObj.getAccountPrivateKey();

    LongAdder count = new LongAdder();
    walletClients = IntStream.range(0, THREAD_COUNT).mapToObj(i -> {
      WalletClient walletClient = new WalletClient();
      walletClient.init(grpcAddress);
      count.increment();
      return walletClient;
    }).collect(Collectors.toList());

    rateLimiter(tps, accountAddress, accountPrivateKey);
  }

  public static void rateLimiter(double tps, String accountAddress, String accountPrivateKey) {
    ListeningExecutorService executorService = MoreExecutors
        .listeningDecorator(Executors.newFixedThreadPool(THREAD_COUNT));
    CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
    RateLimiter limiter = RateLimiter.create(tps);

    for (int i = 0; i < THREAD_COUNT; ++i) {
      executorService
          .execute(new TransferEachOtherTask(walletClients.get(i % THREAD_COUNT), limiter, accountAddress, accountPrivateKey));
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

class TransferEachOtherTask implements Runnable {

  public static final ScheduledExecutorService service = Executors
      .newSingleThreadScheduledExecutor();
  private WalletClient walletClient;
  private RateLimiter limiter;
  private String address;
  private String privateKey;

  private static LongAdder successCount = new LongAdder();
  private static LongAdder failCount = new LongAdder();
  private static LongAdder currentCount = new LongAdder();

  public TransferEachOtherTask(final WalletClient walletClient, RateLimiter limiter, String address, String privateKey) {
    this.walletClient = walletClient;
    this.limiter = limiter;
    this.address = address;
    this.privateKey = privateKey;
  }

  @Override
  public void run() {
    while (true) {
      limiter.acquire();

      byte[] to = Base58.decodeFromBase58Check(address);
      Transaction transaction = walletClient.createTransaction(to, 1, privateKey);

      boolean isSuccess = walletClient.broadcastTransaction(transaction);

      if (isSuccess) {
        successCount.increment();
      } else {
        failCount.increment();
      }

      currentCount.increment();

      if (currentCount.longValue() % 600 == 0) {
        System.out.println("success count: " + successCount.longValue() + ", fail count: " + failCount.longValue());
      }
    }
  }
}

class TransferEachOtherArgs {

  private static final String DEFAULT_CONFIG_FILE_PATH = "config_transfer_each_other.conf";
  private static final String GRPC_ADDRESS = "grpc.address";
  private static final String TPS = "tps";
  private static final String ACCOUNT_ADDRESS = "account.address";
  private static final String ACCOUNT_PRIVATE_KEY = "account.private.key";
  private static final String NET_TYPE = "net.type";

  private static TransferEachOtherArgs INSTANCE;

  @Getter
  @Parameter(names = {"--config"}, description = "Config file path")
  private String config = "";

  @Getter
  @Parameter(names = {"--gRpcAddress"}, description = "gRPC address, like: 127.0.0.1:50051")
  private String gRpcAddress = "";

  @Getter
  @Parameter(names = {
      "--tps"}, description = "tps")
  private int tps = 0;

  @Getter
  @Parameter(names = {"--accountAddress"}, description = "Account address")
  private String accountAddress = "";

  @Getter
  @Parameter(names = {"--accountPrivateKey"}, description = "Account private key")
  private String accountPrivateKey = "";

  @Getter
  @Parameter(names = {"--netType"}, description = "Net type")
  private String netType;

  private TransferEachOtherArgs() {

  }

  public static TransferEachOtherArgs getInstance(String[] args) {
    if (null == INSTANCE) {
      INSTANCE = new TransferEachOtherArgs();
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

    if (StringUtils.isBlank(INSTANCE.gRpcAddress)) {
      INSTANCE.gRpcAddress = config.getString(GRPC_ADDRESS);
    }

    System.out.printf("gRPC address: \u001B[34m%s\u001B[0m", INSTANCE.gRpcAddress);
    System.out.println();

    if (0 == INSTANCE.tps) {
      INSTANCE.tps = config.getInt(TPS);
    }

    System.out.printf("TPS: \u001B[34m%s\u001B[0m", INSTANCE.tps);
    System.out.println();

    if (StringUtils.isBlank(INSTANCE.accountAddress)) {
      INSTANCE.accountAddress = config.getString(ACCOUNT_ADDRESS);
    }

    System.out.printf("Account address: \u001B[34m%s\u001B[0m", INSTANCE.accountAddress);
    System.out.println();

    if (StringUtils.isBlank(INSTANCE.accountPrivateKey)) {
      INSTANCE.accountPrivateKey = config.getString(ACCOUNT_PRIVATE_KEY);
    }

    System.out.printf("Account private key: \u001B[34m%s\u001B[0m", INSTANCE.accountPrivateKey);
    System.out.println();

    if (StringUtils.isBlank(INSTANCE.netType)) {
      INSTANCE.netType = config.getString(NET_TYPE);
    }

    System.out.printf("Net type: \u001B[34m%s\u001B[0m", INSTANCE.netType);
    System.out.println();
  }
}