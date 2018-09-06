package org.tron.core.generator;

import java.util.List;
import org.tron.common.config.TestType;
import org.tron.model.Account;

public class GenerateTransaction {

  /**
   * Transaction type for generating transaction. {@link TestType}.
   */
  private TestType type;

  /**
   * Transaction count for generating transaction.
   */
  private int count;

  /**
   * If generating transaction for trigger contract, must provide target contract address.
   */
  private String contractAddress;

  /**
   * Maybe a lots of owner accounts.
   */
  private List<Account> ownerAccounts;

  /**
   * Maybe a lots of to accounts.
   */
  private List<Account> toAccounts;

  public GenerateTransaction(TestType type, int count,
      List<Account> ownerAccounts, List<Account> toAccounts) {
    this(type, count, null, ownerAccounts, toAccounts);
  }

  public GenerateTransaction(TestType type, int count, String contractAddress,
      List<Account> ownerAccounts, List<Account> toAccounts) {
    this.type = type;
    this.count = count;
    this.contractAddress = contractAddress;
    this.ownerAccounts = ownerAccounts;
    this.toAccounts = toAccounts;
  }

  public void generate() {

  }
}
