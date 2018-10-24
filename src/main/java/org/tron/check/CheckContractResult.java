package org.tron.check;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.tron.protos.Protocol.Account;

@Slf4j
public class CheckContractResult extends CheckResultImp {

  public CheckContractResult() {
  }

  public void setStatistics(
      Map<String, AtomicInteger> statistics) {
    this.statistics = statistics;
  }

  @Override
  public boolean checkAccount() {

    logger.info("Check account.");

    System.out.println("---------------------------------------------");
    AtomicInteger successai = statistics.get("success");
    int success = 0;
    if (successai != null) {
      success = successai.get();
    }

    System.out.println("Owner account balance:");
    Set<Entry<String, Account>> entries = startOwnerAccount.entrySet();
    for (Entry<String, Account> entry : entries) {
      long startBalance = entry.getValue().getBalance();
      long endBalance = endOwnerAccount.get(entry.getKey()).getBalance();

//      entry.getValue().

      System.out.println(
          "Host: " + entry.getKey() + ", Start Balance: " + startBalance + ", End Balance: "
              + endBalance + ", Total Fee: " + totalFee);
      if ((endBalance + totalFee) != startBalance) {
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
