package org.tron.check;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import org.tron.protos.Protocol.Account;

public class CheckResultImp implements CheckResult {

  protected Map<String, Account> startOwnerAccount;
  protected Map<String, Account> startToAccount;
  protected Map<String, Account> endOwnerAccount;
  protected Map<String, Account> endToAccount;
  protected Map<String, AtomicInteger> statistics;
  protected long totalFee;

  @Override
  public boolean checkAccount() {
    return true;
  }

  @Override
  public boolean checkStatistics() {
    return true;
  }

  @Override
  public boolean checkStorage() {
    return true;
  }

  public void setStartOwnerAccount(
      Map<String, Account> startOwnerAccount) {
    this.startOwnerAccount = startOwnerAccount;
  }

  public void setStartToAccount(
      Map<String, Account> startToAccount) {
    this.startToAccount = startToAccount;
  }

  public void setEndOwnerAccount(
      Map<String, Account> endOwnerAccount) {
    this.endOwnerAccount = endOwnerAccount;
  }

  public void setEndToAccount(
      Map<String, Account> endToAccount) {
    this.endToAccount = endToAccount;
  }

  public void setStatistics(
      Map<String, AtomicInteger> statistics) {
    this.statistics = statistics;
  }

  public void setTotalFee(long totalFee) {
    this.totalFee = totalFee;
  }
}
