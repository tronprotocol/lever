package org.tron.common.dispatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.tron.protos.Protocol;

import java.io.IOException;

public class TransactionFactory {
  private static final ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

  private static final Dispatcher dispatcher = context.getBean(Dispatcher.class);

  public static Protocol.Transaction newTransaction() {
    return dispatcher.dispatch().dispatch().dispatch();
  }

  public static void main(String[] args) throws IOException {
    newTransaction();

  }

}
