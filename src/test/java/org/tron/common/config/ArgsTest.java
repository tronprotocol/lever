package org.tron.common.config;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ArgsTest {

  @Parameters
  public static Collection data() {
    return Arrays.asList(new Object[][] {
        {"", ""}
    });
  }

  @Test
  public void testArgs() {
    String[] argv = {"--grpcs", "127.0.0.1:50051,127.0.0.1:50051"};
    Args instance = Args.getInstance(argv);
    System.out.println(instance.getGrpcs());
  }
}
