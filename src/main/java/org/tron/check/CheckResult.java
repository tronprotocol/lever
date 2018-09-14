package org.tron.check;

public interface CheckResult {

  boolean checkAccount();

  boolean checkStatistics();

  boolean checkStorage();
}
