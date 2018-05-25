package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.tron.common.config.Config.ConfigProperty;
import org.tron.common.utils.Base58;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletClient;

public class ExportData {

  private static WalletClient walletClient;

  public static void main(String[] args) throws IOException {
    ExportDataArgs argsObj = ExportDataArgs.getInstance(args);

    walletClient = new WalletClient();
    walletClient.init(argsObj.getGRpcAddress());

    // 读取to address
    List<String> toAddressList = argsObj.getSendTo();
    List<byte[]> toAddressByteList = new ArrayList<>();
    int addressSize = toAddressList.size();
    if (addressSize == 0) {
      System.out.println("address is empty");
      return;
    }

    // 读取 private key
    List<String> privateKeyList = argsObj.getSendFrom();
    int privateKeySize = privateKeyList.size();
    if (privateKeySize == 0) {
      System.out.println("private key is empty");
      return;
    }

    for (String toAddress : toAddressList) {
      byte[] addressBytes = Base58.decodeFromBase58Check(toAddress);
      toAddressByteList.add(addressBytes);
    }
    long amount = argsObj.getSendAmount();

    ConcurrentLinkedQueue<Transaction> transactions = new ConcurrentLinkedQueue<>();
    AtomicInteger counter = new AtomicInteger(0);

    int availProcessors = Runtime.getRuntime().availableProcessors();
    List<Integer> processors = new ArrayList<>();
    for (int i = 0; i < availProcessors; i++) {
      processors.add(i);
    }

    processors.stream().parallel().forEach(item -> {
      for (int i = 0; i < argsObj.getSendTransactionCount() / processors.size(); i++) {
        int c = counter.incrementAndGet();
        Transaction transaction = walletClient
            .createTransaction(toAddressByteList.get(c % addressSize),
                amount, privateKeyList.get(c % privateKeySize));
        transactions.add(transaction);
        if ((c + 1) % 1000 == 0) {
          System.out.println("create transaction current: " + (c + 1));
        }
      }
    });
    counter.getAndSet(0);

    File f = new File(argsObj.getSendOutputDirectory());
    FileOutputStream fos = new FileOutputStream(f);

    for (Transaction transaction : transactions) {
      transaction.writeDelimitedTo(fos);
      long c = counter.incrementAndGet();
      if ((c + 1) % 1000 == 0) {
        System.out.println("write file current: " + (c + 1));
      }
    }
    fos.flush();
    fos.close();
  }
}

class ExportDataArgs {

  private static final String DEFAULT_CONFIG_FILE_PATH = "config_export_data.conf";
  private static final String GRPC_ADDRESS = "grpc.address";
  private static final String SEND_TO = "send.to";
  private static final String SEND_FROM = "send.from";
  private static final String SEND_TRANSACTION_COUNT = "send.transaction.count";
  private static final String SEND_AMOUNT = "send.amount";
  private static final String SEND_OUTPUT_DIRECTORY = "send.output.directory";

  private static ExportDataArgs INSTANCE;

  @Getter
  @Parameter(names = {"--config"}, description = "Config file path")
  private String config = "";

  @Getter
  @Parameter(names = {"--gRpcAddress"}, description = "gRPC address, like: 127.0.0.1:50051")
  private String gRpcAddress = "";

  @Getter
  @Parameter(names = {"--sendTo"}, description = "Send to address")
  private List<String> sendTo = new ArrayList<>();

  @Getter
  @Parameter(names = {"--sendFrom"}, description = "Send from private key")
  private List<String> sendFrom = new ArrayList<>();

  @Getter
  @Parameter(names = {
      "--sendTransactionCount"}, description = "Send transaction count")
  private long sendTransactionCount = 0L;

  @Getter
  @Parameter(names = {
      "--sendAmount"}, description = "Send amount")
  private long sendAmount = 0L;

  @Getter
  @Parameter(names = {"--sendOutputDirectory"}, description = "Send output directory")
  private String sendOutputDirectory = "";

  private ExportDataArgs() {

  }

  public static ExportDataArgs getInstance(String[] args) {
    if (null == INSTANCE) {
      INSTANCE = new ExportDataArgs();
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

    if (0 == INSTANCE.sendTo.size()) {
      INSTANCE.sendTo = config.getStringList(SEND_TO);
    }

    System.out.printf("To: \u001B[34m%s\u001B[0m", INSTANCE.sendTo);
    System.out.println();

    if (0 == INSTANCE.sendFrom.size()) {
      INSTANCE.sendFrom = config.getStringList(SEND_FROM);
    }

    System.out.printf("From: \u001B[34m%s\u001B[0m", INSTANCE.sendFrom);
    System.out.println();

    if (0L == INSTANCE.sendTransactionCount) {
      INSTANCE.sendTransactionCount = config.getLong(SEND_TRANSACTION_COUNT);
    }

    System.out.printf("Count: \u001B[34m%s\u001B[0m", INSTANCE.sendTransactionCount);
    System.out.println();

    if (0L == INSTANCE.sendAmount) {
      INSTANCE.sendAmount = config.getLong(SEND_AMOUNT);
    }

    System.out.printf("Amount: \u001B[34m%s\u001B[0m", INSTANCE.sendAmount);
    System.out.println();

    if (StringUtils.isBlank(INSTANCE.sendOutputDirectory)) {
      INSTANCE.sendOutputDirectory = config.getString(SEND_OUTPUT_DIRECTORY);
    }

    System.out.printf("Output: \u001B[34m%s\u001B[0m", INSTANCE.sendOutputDirectory);
    System.out.println();
  }
}