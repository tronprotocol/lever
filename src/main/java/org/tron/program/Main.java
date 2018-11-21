package org.tron.program;

import com.beust.jcommander.JCommander;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.http.util.Args;
import org.tron.common.utils.ByteArray;
import org.tron.core.exception.CipherException;
import org.tron.module.Result;
import org.tron.protos.Protocol.Transaction;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;
import org.tron.protos.Protocol.Transaction.Result.contractResult;
import org.tron.protos.Protocol.TransactionInfo;
import org.tron.walletserver.WalletApi;

public class Main {

  private static WalletApi client;
  private static List<String> transactionIds = new ArrayList<>();
  private static ConcurrentLinkedQueue<Result> transactionResult = new ConcurrentLinkedQueue<>();

  public static void main(String[] args) throws CipherException {
    Args argsObj = new Args();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    client = new WalletApi(new byte[]{});

    BufferedReader bufferedReader = null;

    try {
      bufferedReader = new BufferedReader(new FileReader("transactionsID.csv"));
      String line = bufferedReader.readLine();
      while (line != null) {
        transactionIds.add(line);
        line = bufferedReader.readLine();
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
    }

    // getTransactionInfoById
    CountDownLatch countDownLatch = new CountDownLatch(transactionIds.size());
    ExecutorService service = Executors.newFixedThreadPool(50);

    transactionIds.forEach(t -> {
      service.execute(() -> {
        Optional<TransactionInfo> transactionInfo = client.getTransactionInfoById(t);
        Optional<Transaction> transaction = client.getTransactionById(t);

        Result result = new Result();

        if (transactionInfo.isPresent() && transaction.isPresent()) {
          TransactionInfo traninfo = transactionInfo.get();
          Transaction tran = transaction.get();
          if (!tran.getRawData().getContractList().isEmpty()) {
            result.setId(ByteArray.toHexString(traninfo.getId().toByteArray()));
            result.setType(tran.getRawData().getContract(0).getType());
            result.setFee(traninfo.getFee());
            result.setEnergyUsageTotal(traninfo.getReceipt().getEnergyUsageTotal());
            result.setEnergyFee(traninfo.getReceipt().getEnergyFee());
            result.setEnergyUsage(traninfo.getReceipt().getEnergyUsage());
            result.setNetFee(traninfo.getReceipt().getNetFee());
            result.setNetUsage(traninfo.getReceipt().getNetUsage());
            if (!tran.getRetList().isEmpty()) {
              result.setSuccess(tran.getRet(0).getContractRet() == contractResult.SUCCESS);
              if (tran.getRet(0).getContractRet() != contractResult.SUCCESS) {
                System.out.println(result.getId());
              }
            }
            transactionResult.add(result);
          }
        }

        if (countDownLatch.getCount() % 100000 == 0) {
          System.out.println(countDownLatch.getCount());
        }

        countDownLatch.countDown();
      });
    });

    try {
      countDownLatch.await();
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
      return;
    }

    int size = transactionResult.size();
    long fee = 0L;
    long energyUsageTotal = 0L;
    long energyFee = 0L;
    long energyUsage = 0L;
    long netFee = 0L;
    long netUsage = 0L;
    long count = 0;

    long fee2 = 0L;
    long energyUsageTotal2 = 0L;
    long energyFee2 = 0L;
    long energyUsage2 = 0L;
    long netFee2 = 0L;
    long netUsage2 = 0L;
    long count2 = 0;

    long fee3 = 0L;
    long energyUsageTotal3 = 0L;
    long energyFee3 = 0L;
    long energyUsage3 = 0L;
    long netFee3 = 0L;
    long netUsage3 = 0L;
    long count3 = 0;

    for (int i = 0; i < size; i++) {
      Result result = transactionResult.poll();

      if (result.getType() == ContractType.TriggerSmartContract) {
        count3++;
        fee3 += result.getFee();
        energyUsageTotal3 += result.getEnergyUsageTotal();
        energyFee3 += result.getEnergyFee();
        energyUsage3 += result.getEnergyUsage();
        netFee3 += result.getNetFee();
        netUsage3 += result.getNetUsage();
      } else if (result.getType() == ContractType.ExchangeTransactionContract) {
        count2++;
        fee2 += result.getFee();
        energyUsageTotal2 += result.getEnergyUsageTotal();
        energyFee2 += result.getEnergyFee();
        energyUsage2 += result.getEnergyUsage();
        netFee2 += result.getNetFee();
        netUsage2 += result.getNetUsage();
      }

      count++;
      fee += result.getFee();
      energyUsageTotal += result.getEnergyUsageTotal();
      energyFee += result.getEnergyFee();
      energyUsage += result.getEnergyUsage();
      netFee += result.getNetFee();
      netUsage += result.getNetUsage();

      if (i % 100000 == 0) {
        System.out.println(i);
      }
    }

    System.out.println();
    System.out.println("成功交易：");
    System.out.println("总数：" + count);
    System.out.println("消耗fee总量：" + fee);
    System.out.println("消耗EnergyUsageTotal总量：" + energyUsageTotal);
    System.out.println("消耗EnergyFee(SUN)总消耗：" + energyFee);
    System.out.println("EnergyUsage总消耗：" + energyUsage);
    System.out.println("NetFee总消耗：" + netFee);
    System.out.println("NetUsage总消耗：" + netUsage);
    System.out.println();

    System.out.println();
    System.out.println("ExchangeTransactionContract：");
    System.out.println("总数：" + count2);
    System.out.println("消耗fee总量：" + fee2);
    System.out.println("消耗EnergyUsageTotal总量：" + energyUsageTotal2);
    System.out.println("消耗EnergyFee(SUN)总消耗：" + energyFee2);
    System.out.println("EnergyUsage总消耗：" + energyUsage2);
    System.out.println("NetFee总消耗：" + netFee2);
    System.out.println("NetUsage总消耗：" + netUsage2);
    System.out.println();

    System.out.println();
    System.out.println("TriggerSmartContract：");
    System.out.println("总数：" + count3);
    System.out.println("消耗fee总量：" + fee3);
    System.out.println("消耗EnergyUsageTotal总量：" + energyUsageTotal3);
    System.out.println("消耗EnergyFee(SUN)总消耗：" + energyFee3);
    System.out.println("EnergyUsage总消耗：" + energyUsage3);
    System.out.println("NetFee总消耗：" + netFee3);
    System.out.println("NetUsage总消耗：" + netUsage3);
    System.out.println();

    System.exit(0);
  }
}
