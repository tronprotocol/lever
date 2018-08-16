package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import io.netty.util.internal.ConcurrentSet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.module.Account;

public class GenerateAccount {

  public static void main(String[] args) throws IOException {
    Args argsObj = new Args();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    int accountCount = argsObj.getCount();

    ConcurrentSet<Account> accounts = new ConcurrentSet<>();
    AtomicInteger counter = new AtomicInteger(0);

    int availProcessors = Runtime.getRuntime().availableProcessors();
    List<Integer> processors = new ArrayList<>();
    for (int i = 0; i < availProcessors; i++) {
      processors.add(i);
    }

    processors.stream().parallel().forEach(item -> {
      for (int i = 0; i < accountCount / processors.size(); i++) {
        int c = counter.incrementAndGet();

        ECKey newKey = new ECKey();

        Account account = new Account(ByteArray.toHexString(newKey.getPrivKeyBytes()), ByteArray.toHexString(newKey.getAddress()));

        accounts.add(account);

        if ((c + 1) % 1000 == 0) {
          System.out.println("create account current: " + (c + 1) + ", total accounts: " + accounts.size());
        }
      }
    });
    counter.getAndSet(0);

    File f = new File("accounts.txt");
    FileOutputStream fos = new FileOutputStream(f);
    ObjectOutputStream oos = new ObjectOutputStream(fos);

    for (Account account : accounts) {
      long c = counter.incrementAndGet();

      oos.writeObject(account);

      if ((c + 1) % 1000 == 0) {
        System.out.println("write file current: " + (c + 1));
      }
    }

    oos.flush();
    oos.close();
  }

  public static class Args {
    @Getter
    @Parameter(names = {
        "--count"}, description = "Account count", required = true)
    private int count;
  }
}
