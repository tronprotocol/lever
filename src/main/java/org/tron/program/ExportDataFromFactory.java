package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.tron.Validator.LongValidator;
import org.tron.Validator.StringValidator;
import org.tron.common.crypto.Hash;
import org.tron.common.dispatch.TransactionFactory;
import org.tron.common.dispatch.creator.CreatorCounter;
import org.tron.common.utils.CsvUtils;
import org.tron.core.config.Parameter.CommonConstant;
import org.tron.protos.Protocol.Transaction;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class ExportDataFromFactory {

  //Example:
  //--toaddress toaddress.csv --amount 1 --output trxsdata.csv --count 10000 --privatekey privatekey.csv
  public static void main(String[] args) throws Exception {
    long start = System.currentTimeMillis();
    AtomicLong counter = new AtomicLong(0);
    Args argsObj = new Args();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    if (CommonConstant.NET_TYPE_MAINNET.equals(argsObj.getNetType())) {
      Hash.changeAddressPrefixMainnet();
    }

    ConcurrentLinkedQueue<Transaction> transactions = new ConcurrentLinkedQueue<>();

    File f = new File(argsObj.getOutput());
    FileOutputStream fos = new FileOutputStream(f);
    CountDownLatch countDownLatch = new CountDownLatch((int) argsObj.getCount());
    ExecutorService service = Executors.newFixedThreadPool(50);
    LongStream.range(0L, argsObj.getCount()).forEach(l -> {
      service.execute(() -> {
        long c = counter.incrementAndGet();
        if ((c + 1) % 1000 == 0) {
          System.out.println("create trx current: " + (c + 1));
        }

        Optional.ofNullable(TransactionFactory.newTransaction()).ifPresent(transactions::add);
        countDownLatch.countDown();
      });
    });
    countDownLatch.await();
    counter.set(0L);
    for (Transaction transaction : transactions) {
      transaction.writeDelimitedTo(fos);
      long c = counter.incrementAndGet();
      if ((c + 1) % 1000 == 0) {
        System.out.println("write file current: " + (c + 1));
      }
    }

    fos.flush();
    fos.close();
    System.out.println(
        "create " + argsObj.getCount() + " trx need cost:" + (System.currentTimeMillis() - start)
            + "ms");

    TransactionFactory.context.getBean(CreatorCounter.class).getCounterMap().entrySet().stream().forEach((v) -> {
      System.out.println(v.getKey() + ": " + v.getValue().longValue());
    });

    System.exit(0);
  }

  private static List<String> getStrings(String filePath) {
    List<CSVRecord> read = CsvUtils.read(new File(filePath));
    List<String> stringList = new ArrayList<>();

    read.forEach(l -> stringList.add(l.get(0)));

    return stringList;
  }

  private static class Args {

    @Getter
    @Parameter(names = {"--count",
        "-c"}, description = "Count", required = true, validateWith = LongValidator.class)
    private long count;

    @Getter
    @Parameter(names = {"--output",
        "-o"}, description = "Save data file", required = true, validateWith = StringValidator.class)
    private String output;

    @Getter
    @Parameter(names = {"--netType"}, description = "Net type", required = true, validateWith = StringValidator.class)
    private String netType;
  }
}

