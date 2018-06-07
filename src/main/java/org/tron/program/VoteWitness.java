package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import com.google.protobuf.ByteString;
import com.typesafe.config.Config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
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
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Contract;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletClient;

public class VoteWitness {

  public final static long FROZEN_DURATION = 3;

  private static List<WalletClient> walletClients = new ArrayList<>();

  public static void main(String[] args) throws IOException {
    VoteWitnessArgs argsObj = VoteWitnessArgs.getInstance(args);

    VoteWitnessTask.accountCount = argsObj.getAccountCount();

    int threadCount = argsObj.getThreadCount();
    String grpcAddress = argsObj.getGRpcAddress();
    int accountCount = argsObj.getAccountCount();
    String privateKey = argsObj.getOwnerPrivateKey();
    int voteCount = argsObj.getVoteCount();
    List<String> voteWitness = argsObj.getVoteWitness();
    double tps = argsObj.getTps();

    LongAdder count = new LongAdder();
    walletClients = IntStream.range(0, threadCount).mapToObj(i -> {
      WalletClient walletClient = new WalletClient();
      walletClient.init(grpcAddress);
      count.increment();
      return walletClient;
    }).collect(Collectors.toList());

    boolean isFreezeSuccess = freezeBalance(walletClients.get(0), privateKey,
        getFreezeBalance(accountCount, voteCount));

    if (!isFreezeSuccess) {
      System.out.println("freeze balance failed");
      return;
    }

    start(threadCount, accountCount, privateKey, voteCount, voteWitness, tps);
  }

  public static boolean freezeBalance(WalletClient walletClient, String privateKey,
      long frozenBalance) {
    return walletClient
        .freezeBalance(privateKey, frozenBalance, FROZEN_DURATION);
  }

  public static long getFreezeBalance(int accountCount, int voteCount) {
    long result = accountCount * voteCount * 1_000_000;

    return result;
  }

  public static void start(int threadCount, int accountCount, String privateKey, int voteCount,
      List<String> voteWitness, double tps) {
    ListeningExecutorService executorService = MoreExecutors
        .listeningDecorator(Executors.newFixedThreadPool(threadCount));
    CountDownLatch latch = new CountDownLatch(threadCount);
    RateLimiter limiter = RateLimiter.create(tps);

    for (int count = 0; count < accountCount; ++count) {
      executorService.execute(
          new VoteWitnessTask(walletClients.get(count % threadCount), limiter, privateKey, voteCount,
              voteWitness.get(count % voteWitness.size())));
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

class VoteWitnessTask implements Runnable {

  private WalletClient walletClient;

  private String privateKey;

  public static int accountCount;

  private int voteCount;

  private String witness;

  private RateLimiter limiter;

  private static LongAdder voteSuccess = new LongAdder();
  private static LongAdder voteFail = new LongAdder();
  private static LongAdder freezeFail = new LongAdder();
  private static LongAdder createFail = new LongAdder();
  private static LongAdder total = new LongAdder();

  private static Date startTime;
  private static Date endTime;

  public static final ScheduledExecutorService service = Executors
      .newSingleThreadScheduledExecutor();

  static {
    service.scheduleAtFixedRate(() -> {
      System.out.println(
          "current: " + total.longValue()
              + ", vote success: " + voteSuccess.longValue()
              + ", vote fail: " + voteFail.longValue()
              + ", freeze fail: " + freezeFail
              + ", create fail: " + createFail);

      if (total.longValue() == accountCount) {
        endTime = new Date();
        System.out.printf(
            "\u001B[36mstart time:\u001B[0m %tF %tT, \u001B[36mend time:\u001B[0m %tF %tT\n",
            startTime, startTime, endTime,
            endTime);

        service.shutdown();
      }
    }, 5, 5, TimeUnit.SECONDS);
  }

  public VoteWitnessTask() {

  }

  public VoteWitnessTask(WalletClient walletClient, RateLimiter limiter, String privateKey, int voteCount,
      String witness) {
    this.walletClient = walletClient;
    this.limiter = limiter;
    this.privateKey = privateKey;
    this.voteCount = voteCount;
    this.witness = witness;
  }

  @Override
  public void run() {
    limiter.acquire();

    if (0L == total.longValue()) {
      startTime = new Date();
    }

    ECKey newKey = new ECKey();

    Transaction transaction = walletClient
        .createTransaction(newKey.getAddress(), voteCount * 1_000_000, privateKey);

    boolean isSuccess = walletClient.broadcastTransaction(transaction);

    if (isSuccess) {
      boolean isFreezeSuccess = walletClient
          .freezeBalance(ByteArray.toHexString(newKey.getPrivKeyBytes()), voteCount * 1_000_000,
              VoteWitness.FROZEN_DURATION);

      if (isFreezeSuccess) {
        VoteWitnessTask voteWitnessTask = new VoteWitnessTask();
        HashMap<String, String> witnessMap = new HashMap<>();
        witnessMap.put(witness, "" + voteCount);
        Contract.VoteWitnessContract voteWitnessContract = voteWitnessTask
            .createVoteWitnessContract(newKey.getAddress(), witnessMap);
        Transaction voteWitnessTransaction = walletClient
            .voteWitnessTransaction(voteWitnessContract);
        if (voteWitnessTransaction == null
            || voteWitnessTransaction.getRawData().getContractCount() == 0) {
          voteFail.increment();
          total.increment();
          return;
        }

        voteWitnessTransaction = walletClient.signTransaction(voteWitnessTransaction, newKey);

        boolean isVoteSuccess = walletClient.broadcastTransaction(voteWitnessTransaction);

        if (isVoteSuccess) {
          voteSuccess.increment();
        } else {
          voteFail.increment();
        }
      } else {
        freezeFail.increment();
      }
    } else {
      createFail.increment();
    }

    total.increment();
  }

  public Contract.VoteWitnessContract createVoteWitnessContract(byte[] owner,
      HashMap<String, String> witness) {
    Contract.VoteWitnessContract.Builder builder = Contract.VoteWitnessContract.newBuilder();
    builder.setOwnerAddress(ByteString.copyFrom(owner));
    for (String addressBase58 : witness.keySet()) {
      String value = witness.get(addressBase58);
      long count = Long.parseLong(value);
      Contract.VoteWitnessContract.Vote.Builder voteBuilder = Contract.VoteWitnessContract.Vote
          .newBuilder();
      byte[] address = Base58.decodeFromBase58Check(addressBase58);
      if (address == null) {
        continue;
      }
      voteBuilder.setVoteAddress(ByteString.copyFrom(address));
      voteBuilder.setVoteCount(count);
      builder.addVotes(voteBuilder.build());
    }

    return builder.build();
  }
}

class VoteWitnessArgs {

  private static final String DEFAULT_CONFIG_FILE_PATH = "config_vote_witness.conf";
  private static final String GRPC_ADDRESS = "grpc.address";
  private static final String THREAD_COUNT = "thread.count";
  private static final String ACCOUNT_COUNT = "account.count";
  private static final String OWNER_PRIVATE_KEY = "owner.private.key";
  private static final String VOTE_COUNT = "vote.count";
  private static final String VOTE_WITNESS = "vote.witness";
  private static final String TPS = "tps";

  private static VoteWitnessArgs INSTANCE;

  @Getter
  @Parameter(names = {"--config"}, description = "Config file path")
  private String config = "";

  @Getter
  @Parameter(names = {"--gRpcAddress"}, description = "gRPC address, like: 127.0.0.1:50051")
  private String gRpcAddress = "";

  @Getter
  @Parameter(names = {"--threadCount"}, description = "Thread count")
  private int threadCount = 0;

  @Getter
  @Parameter(names = {"--accountCount"}, description = "Account count")
  private int accountCount = 0;

  @Getter
  @Parameter(names = {"--ownerPrivateKey"}, description = "Owner private key")
  private String ownerPrivateKey = "";

  @Getter
  @Parameter(names = {"--voteCount"}, description = "Vote count")
  private int voteCount = 0;

  @Getter
  @Parameter(names = {"--voteWitness"}, description = "Vote witness")
  private List<String> voteWitness = new ArrayList<>();

  @Getter
  @Parameter(names = {"--tps"}, description = "TPS")
  private int tps = 0;

  private VoteWitnessArgs() {

  }

  public static VoteWitnessArgs getInstance(String[] args) {
    if (null == INSTANCE) {
      INSTANCE = new VoteWitnessArgs();
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

    if (0 == INSTANCE.threadCount) {
      INSTANCE.threadCount = config.getInt(THREAD_COUNT);
    }

    System.out.printf("Thread count: \u001B[34m%s\u001B[0m", INSTANCE.threadCount);
    System.out.println();

    if (0 == INSTANCE.accountCount) {
      INSTANCE.accountCount = config.getInt(ACCOUNT_COUNT);
    }

    System.out.printf("Account count: \u001B[34m%s\u001B[0m", INSTANCE.accountCount);
    System.out.println();

    if (StringUtils.isBlank(INSTANCE.ownerPrivateKey)) {
      INSTANCE.ownerPrivateKey = config.getString(OWNER_PRIVATE_KEY);
    }

    System.out.printf("Owner short private key: \u001B[34m%s\u001B[0m",
        INSTANCE.ownerPrivateKey.substring(0, 7));
    System.out.println();

    if (0L == INSTANCE.voteCount) {
      INSTANCE.voteCount = config.getInt(VOTE_COUNT);
    }

    System.out.printf("Vote count: \u001B[34m%s\u001B[0m", INSTANCE.voteCount);
    System.out.println();

    if (0 == INSTANCE.voteWitness.size()) {
      INSTANCE.voteWitness = config.getStringList(VOTE_WITNESS);
    }

    System.out.printf("Vote witness: \u001B[34m%s\u001B[0m", INSTANCE.voteWitness);
    System.out.println();

    if (0 == INSTANCE.tps) {
      INSTANCE.tps = config.getInt(TPS);
    }

    System.out.printf("TPS: \u001B[34m%s\u001B[0m", INSTANCE.tps);
    System.out.println();
  }
}