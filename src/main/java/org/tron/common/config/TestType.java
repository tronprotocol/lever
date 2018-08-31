package org.tron.common.config;


import org.tron.common.exception.LeverException;

/**
 * Initialization according to test type.
 */
public enum TestType {
  TRANSFER_BALANCE("transfer_balance"),
  TRANSFER_ASSET("transfer_asset"),
  DEPLOY_CONTRACT("deploy_contract"),
  TRIGGER_CONTRACT("trigger_contract"),
  AIRDROP("airdrop"),
  OTHERS("others");

  private String name;

  TestType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public static TestType getByName(String name) throws LeverException {
    if (null == name) {
      return OTHERS;
    }

    for (TestType testType : values()) {
      if (testType.getName().equals(name)) {
        return testType;
      }
    }

    return OTHERS;
  }
}
