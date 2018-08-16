package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.tron.Validator.StringValidator;
import org.tron.common.utils.ByteArray;
import org.tron.protos.Protocol.TransactionInfo;
import org.tron.service.WalletGrpcClient;

public class CheckTransactionInfo {
  private static WalletGrpcClient client;
  private static List<String> transactionIds = new ArrayList<>();
  private static ConcurrentHashMap<String, TransactionInfo> transactionInfoMap = new ConcurrentHashMap<>();

  public static void main(String[] args) {
    Args argsObj = new Args();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    client = new WalletGrpcClient(argsObj.getGRPC());

    BufferedReader bufferedReader = null;
    ProgressBar pb = new ProgressBar("Reading transactions ID file", -1, ProgressBarStyle.ASCII);

    try {
      bufferedReader = new BufferedReader(new FileReader(argsObj.getDataFile()));
      String line = bufferedReader.readLine();
      while (line != null) {
        transactionIds.add(line);
        line = bufferedReader.readLine();
        pb.step();
      }

    } catch (IOException e) {
      e.printStackTrace();
      return;
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          e.printStackTrace();
          return;
        }
      }

      try {
        pb.close();
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
        return;
      }
    }

    // getTransactionInfoById
    CountDownLatch countDownLatch = new CountDownLatch(transactionIds.size());
    ExecutorService service = Executors.newFixedThreadPool(50);

    ProgressBar generationPb = new ProgressBar("Getting transaction info", transactionIds.size(), ProgressBarStyle.ASCII);

    transactionIds.forEach(t -> {
      service.execute(() -> {
        TransactionInfo transactionInfo = client.getTransactionInfoById(t);
        transactionInfoMap.put(ByteArray.toHexString(transactionInfo.getId().toByteArray()), transactionInfo);
        generationPb.step();
        countDownLatch.countDown();
      });
    });

    try {
      countDownLatch.await();
      generationPb.close();
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
      return;
    }

    long fee = 0L;

    for (Entry<String, TransactionInfo> entry : transactionInfoMap.entrySet()) {
      fee += entry.getValue().getFee();
    }

    System.out.println("fee:" + fee);

    System.exit(0);
  }

  public static class Args {

    @Getter
    @Parameter(names = {
        "--grpc"}, description = "gRPC host", required = true, validateWith = StringValidator.class)
    private String gRPC;

    @Getter
    @Parameter(names = {
        "--dataFile"}, description = "Transactions ID file", required = true, validateWith = StringValidator.class)
    private String dataFile;
  }
}
