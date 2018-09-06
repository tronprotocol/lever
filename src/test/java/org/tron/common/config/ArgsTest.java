package org.tron.common.config;

import java.util.Arrays;
import java.util.Collection;
import lombok.AllArgsConstructor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
@AllArgsConstructor
public class ArgsTest {

  private String grpcArgc;
  private String grpcArgv;
  private int grpcSize;

  @Parameters
  public static Collection data() {
    return Arrays.asList(new Object[][] {
        {"--grpcs", "127.0.0.1:50051,127.0.0.1:50052", 2},
        {"--grpcs", "127.0.0.1:50051", 1},
        {"--grpcs", "", 1},
        {"--grpcs", null, 0}
    });
  }

  @Test
  public void testArgs() {
    String[] argv = {this.grpcArgc, this.grpcArgv};
    if (null != this.grpcArgv) {
      Args instance = Args.getInstance(argv);
      Assert.assertEquals(instance.getGrpcs().size(), this.grpcSize);
    } else {
      try {
        Args.getInstance(argv);
      } catch (NullPointerException e) {
        Assert.assertTrue(true);
      }
    }
  }

  @After
  public void clearArgs() {
    Args.clear();
  }
}
