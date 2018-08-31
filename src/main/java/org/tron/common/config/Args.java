package org.tron.common.config;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class Args {

  private static volatile Args instance = null;

  private Args(String[] args) {
    JCommander.newBuilder()
        .addObject(this)
        .build()
        .parse(args);
  }

  public static Args getInstance(String[] args) {
    if (null == instance) {
      synchronized (Args.class) {
        if (null == instance) {
          instance = new Args(args);
        }
      }
    }

    return instance;
  }

  public static Args getInstance() {
    return getInstance(null);
  }

  @Parameter(names = {"--grpcs"}, description = "gRPC host like: 127.0.0.1:50051", required = true)
  private List<String> grpcs = new ArrayList<>();
}
