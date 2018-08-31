package org.tron.common.config;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.tron.common.exception.LeverException;

@RunWith(Parameterized.class)
public class TestTypeTest {

  private String name;
  private TestType testType;

  public TestTypeTest(String name, TestType testType) {
    this.name = name;
    this.testType = testType;
  }

  @Parameters
  public static Collection data() {
    return Arrays.asList(new Object[][] {
        {"transfer_balance", TestType.TRANSFER_BALANCE},
        {"transfer_asset", TestType.TRANSFER_ASSET},
        {"deploy_contract", TestType.DEPLOY_CONTRACT},
        {"trigger_contract", TestType.TRIGGER_CONTRACT},
        {"others", TestType.OTHERS},
        {null, TestType.OTHERS},
        {"abc", TestType.OTHERS}
    });
  }

  @Test
  public void testGetByName() throws LeverException {
    Assert.assertEquals(this.testType, TestType.getByName(this.name));
  }
}
