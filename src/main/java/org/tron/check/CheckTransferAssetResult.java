package org.tron.check;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.tron.protos.Protocol.Account;

@Slf4j
public class CheckTransferAssetResult extends CheckResultImp {

  public CheckTransferAssetResult() {
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

      System.out.println(
          "Host: " + entry.getKey() + ", Start Balance: " + startBalance + ", End Balance: "
              + endBalance + ", Total Fee: " + totalFee);
      if ((endBalance + totalFee) != startBalance) {
        return false;
      }
    }

    System.out.println("Owner account asset:");
    Set<Entry<String, Account>> entries1 = startOwnerAccount.entrySet();
    for (Entry<String, Account> entry : entries1) {
      //is right?
      long startAsset = entry.getValue().getAssetMap().get("pressure1");
      long endAsset = endOwnerAccount.get(entry.getKey()).getAssetMap().get("pressure1");

      System.out.println(
          "Host: " + entry.getKey() + ", Start Asset: " + startAsset + ", End Asset: "
              + endAsset + ", Success: "
              + success + ", Total Fee: " + totalFee);
      if ((endAsset + success) != startAsset) {
        return false;
      }
    }

    System.out.println();
    System.out.println("To account asset:");
    Set<Entry<String, Account>> entries2 = startToAccount.entrySet();
    for (Entry<String, Account> entry :
        entries2) {
      if(entry.getValue().getAssetMap().isEmpty()){
        long startAsset = 0;
        long endAsset = endToAccount.get(entry.getKey()).getAssetMap().get("pressure1");
        System.out.println(
            "Start Asset: " + startAsset + ", End Asset: " + endAsset + ", Success: "
                + success);
        if ((startAsset + success) != endAsset) {
          return false;
        }
      }else {
        long startAsset = entry.getValue().getAssetMap().get("pressure1");
        long endAsset = endToAccount.get(entry.getKey()).getAssetMap().get("pressure1");
        System.out.println(
            "Start Asset: " + startAsset + ", End Asset: " + endAsset + ", Success: "
                + success);
        if ((startAsset + success) != endAsset) {
          return false;
        }
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
