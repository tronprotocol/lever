package org.tron.common.config;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import lombok.Getter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.tron.common.config.Config.ConfigProperty;

public class Args {

  private static final String DEFAULT_CONFIG_FILE_PATH = "config.conf";
  private static final String GRPC_STRESS_ADDRESS = "grpc.stress.address";
  private static final String GRPC_CHECK_ADDRESS = "grpc.check.address";
  private static final String THREAD_COUNT = "thread.count";
  private static final String NET_TYPE = "net.type";
  private static final String STRESS_TYPE = "stress.type";
  private static final String STRESS_COUNT = "stress.count";
  private static final String STRESS_TPS = "stress.tps";
  private static final String STRESS_RETRY = "stress.retry";
  private static final String CHECK_OWNER_ACCOUNT_ADDRESS = "check.owner.account.address";
  private static final String CHECK_TO_ACCOUNT_ADDRESS = "check.to.account.address";

  private static Args INSTANCE;

  @Getter
  @com.beust.jcommander.Parameter(names = {"--config"}, description = "Config file path")
  private String config = "";

  @Getter
  @com.beust.jcommander.Parameter(names = {
      "--grpc-stress-address"}, description = "gRPC address, like: 127.0.0.1:50051")
  private List<String> gRpcStressAddress = new ArrayList<>();

  @Getter
  @com.beust.jcommander.Parameter(names = {
      "--grpc-check-address"}, description = "gRPC address, like: 127.0.0.1:50051")
  private List<String> gRpcCheckAddress = new ArrayList<>();

  @Getter
  @com.beust.jcommander.Parameter(names = {"--thread-count"}, description = "Thread count")
  private int threadCount = 0;

  @Getter
  @Parameter(names = {
      "--stress-type"}, description = "Stress type")
  private String stressType = null;

  @Getter
  @com.beust.jcommander.Parameter(names = {
      "--stress-count"}, description = "count")
  private int stressCount = 0;

  @Getter
  @Parameter(names = {
      "--net-type"}, description = "Net type")
  private String netType = null;

  @Getter
  @com.beust.jcommander.Parameter(names = {
      "--stress-tps"}, description = "tps")
  private int stressTps = 0;

  @Getter
  @Parameter(names = {"--stress-retry"}, description = "Retry")
  private boolean retry = false;

  @Getter
  @com.beust.jcommander.Parameter(names = {
      "--check-owner-account-address"}, description = "Check owner account address")
  private String checkOwnerAccountAddress = "";

  @Getter
  @com.beust.jcommander.Parameter(names = {
      "--check-to-account-address"}, description = "Check to account address")
  private String checkToAccountAddress = "";

  private Args() {

  }

  public static Args getInstance(String[] args) {
    if (null == INSTANCE) {
      INSTANCE = new Args();
      JCommander.newBuilder().addObject(INSTANCE).build().parse(args);
      INSTANCE.initArgs();
    }

    return INSTANCE;
  }

  public static Args getInstance() {
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

    com.typesafe.config.Config config = (com.typesafe.config.Config) configInfo
        .get(ConfigProperty.CONFIG);
    String configTip = (String) configInfo.get(ConfigProperty.TIP);

    System.out.printf("Loading config file: \u001B[34m%s\u001B[0m", configTip);
    System.out.println();

    if (CollectionUtils.isEmpty(INSTANCE.gRpcStressAddress)) {
      INSTANCE.gRpcStressAddress = config.getStringList(GRPC_STRESS_ADDRESS);
    }

    System.out.printf("Stress gRPC address: \u001B[34m%s\u001B[0m", INSTANCE.gRpcStressAddress);
    System.out.println();

    if (CollectionUtils.isEmpty(INSTANCE.gRpcCheckAddress)) {
      INSTANCE.gRpcCheckAddress = config.getStringList(GRPC_CHECK_ADDRESS);
    }

    System.out.printf("Check gRPC address: \u001B[34m%s\u001B[0m", INSTANCE.gRpcCheckAddress);
    System.out.println();

    if (0 == INSTANCE.threadCount) {
      INSTANCE.threadCount = config.getInt(THREAD_COUNT);
    }

    System.out.printf("Thread count: \u001B[34m%s\u001B[0m", INSTANCE.threadCount);
    System.out.println();

    if (null == INSTANCE.stressType) {
      INSTANCE.stressType = config.getString(STRESS_TYPE);
    }

    System.out.printf("Stress type: \u001B[34m%s\u001B[0m", INSTANCE.stressType);
    System.out.println();

    if (0 == INSTANCE.stressCount) {
      INSTANCE.stressCount = config.getInt(STRESS_COUNT);
    }

    System.out.printf("Stress count: \u001B[34m%s\u001B[0m", INSTANCE.stressCount);
    System.out.println();

    if (null == INSTANCE.netType) {
      INSTANCE.netType = config.getString(NET_TYPE);
    }

    System.out.printf("Net type: \u001B[34m%s\u001B[0m", INSTANCE.netType);
    System.out.println();

    if (0 == INSTANCE.stressTps) {
      INSTANCE.stressTps = config.getInt(STRESS_TPS);
    }

    System.out.printf("TPS: \u001B[34m%s\u001B[0m", INSTANCE.stressTps);
    System.out.println();

    System.out.printf("Retry: \u001B[34m%s\u001B[0m", INSTANCE.retry);
    System.out.println();

    if ("" == INSTANCE.checkOwnerAccountAddress) {
      INSTANCE.checkOwnerAccountAddress = config.getString(CHECK_OWNER_ACCOUNT_ADDRESS);
    }

    System.out
        .printf("Check owner account address: \u001B[34m%s\u001B[0m",
            INSTANCE.checkOwnerAccountAddress);
    System.out.println();

    if ("" == INSTANCE.checkToAccountAddress) {
      INSTANCE.checkToAccountAddress = config.getString(CHECK_TO_ACCOUNT_ADDRESS);
    }

    System.out
        .printf("Check to account address: \u001B[34m%s\u001B[0m",
            INSTANCE.checkToAccountAddress);
    System.out.println();
  }
}
