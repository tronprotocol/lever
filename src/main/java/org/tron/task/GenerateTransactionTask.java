package org.tron.task;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.LongStream;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tron.common.config.Args;
import org.tron.common.dispatch.TransactionFactory;
import org.tron.protos.Protocol.Transaction;

public class GenerateTransactionTask implements Task {

  private static final Logger logger = LoggerFactory.getLogger("GenerateTransactionTask");

  private ConcurrentLinkedQueue<Transaction> transactions = new ConcurrentLinkedQueue<>();

  private int count;

  public GenerateTransactionTask(int count) {
    logger.info("Create task: {}.", getClass().getSimpleName());

    this.count = count;
  }

  @Override
  public void init(Args args) {
    logger.info("Init generate transaction task.");

    initTransactionFactory(args.getStressType());
  }

  private void initTransactionFactory(String stressType) {
    logger.info("Init transaction factor: {}.", stressType);

    String context = "";

    if (stressType.equalsIgnoreCase("transfer.balance")) {
      context = "context/TransferTRX.xml";
    } else if (stressType.equalsIgnoreCase("transfer.asset")) {
      context = "context/TransferAsset.xml";
    }

    logger.info("Load context: {}.", context);

    TransactionFactory.init(context);
  }

  @Override
  public void start() {
    logger.info("Start generate transaction.");

    CountDownLatch countDownLatch = new CountDownLatch(this.count);
    ExecutorService service = Executors.newFixedThreadPool(50);

    ProgressBar generationPb = new ProgressBar("Generating transactions", this.count,
        ProgressBarStyle.ASCII);

    LongStream.range(0L, this.count).forEach(l -> {
      service.execute(() -> {
        generationPb.step();
        Optional.ofNullable(TransactionFactory.newTransaction()).ifPresent(transactions::add);
        countDownLatch.countDown();
      });
    });

    try {
      countDownLatch.await();
      generationPb.close();
      Thread.sleep(1000);
      System.out.println();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void shutdown() {

  }

  public ConcurrentLinkedQueue<Transaction> getTransactions() {
    return transactions;
  }
}
