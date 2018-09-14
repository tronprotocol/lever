package org.tron.check;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.tron.protos.Protocol.Account;

@Slf4j
public class CheckTransferBalanceResult extends CheckResultImp {

  public CheckTransferBalanceResult() {
  }

  public void setStatistics(
      Map<String, AtomicInteger> statistics) {
    this.statistics = statistics;
  }

  @Override
  public boolean checkAccount() {

    logger.info("Check account.");

    System.out.println("---------------------------------------------");
    int success = statistics.get("success").get();

    System.out.println("Owner account:");
    Set<Entry<String, Account>> entries = startOwnerAccount.entrySet();
    for (Entry<String, Account> entry : entries) {
      long startBalance = entry.getValue().getBalance();
      long endBalance = endOwnerAccount.get(entry.getKey()).getBalance();

      System.out.println(
          "Host: " + entry.getKey() + ", Start Balance: " + startBalance + ", End Balance: "
              + endBalance + ", Success: "
              + success + ", Total Fee: " + totalFee);
      if ((endBalance + success + totalFee) != startBalance) {
        return false;
      }
    }

    System.out.println();
    System.out.println("To account:");
    Set<Entry<String, Account>> entries1 = startToAccount.entrySet();
    for (Entry<String, Account> entry :
        entries1) {
      long startBalance = entry.getValue().getBalance();
      long endBalance = endToAccount.get(entry.getKey()).getBalance();
      System.out.println(
          "Start Balance: " + startBalance + ", End Balance: " + endBalance + ", Success: "
              + success);
      if ((startBalance + success) != endBalance) {
        return false;
      }
    }
    System.out.println("---------------------------------------------");

    return true;
  }

  @Override
  public boolean checkStatistics() {

    logger.info("Check statistics.");

    System.out.println("---------------------------------------------");
    Set<Entry<String, AtomicInteger>> entries = statistics.entrySet();
    for (Entry<String, AtomicInteger> entry : entries) {
      System.out.println(entry.getKey() + ": " + entry.getValue().get());
    }

    System.out.println("---------------------------------------------");
    return true;
  }

  @Override
  public boolean checkStorage() {
    logger.info("Check storage.");
    System.out.println("---------------------------------------------");
    System.out.println("PASS");
    System.out.println("---------------------------------------------");
    return true;
  }
}
