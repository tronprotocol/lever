package org.tron.program;

import org.tron.common.config.Args;
import org.tron.module.Application;

public class Lever {

  public static void main(String[] args) {
    Args instance = Args.getInstance(args);

    Application application = new Application(instance);

    application.init();
    application.start();
  }
}
