package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import lombok.Getter;
import org.tron.Validator.StringValidator;
import org.tron.common.utils.Sha256Hash;
import org.tron.protos.Protocol.Transaction;

//Example --datafile [path to trxsdata.csv]
public class ShowData {

  private static Map<Sha256Hash, LongAdder> transactionsMap = new HashMap<>();

  public static void main(String[] args) throws IOException {
    ShowDataArgs args1 = new ShowDataArgs();
    JCommander.newBuilder().addObject(args1).build().parse(args);

    File f = new File(args1.getDataFile());
    FileInputStream fis = new FileInputStream(f);

    Transaction transaction;
    while ((transaction = Transaction.parseDelimitedFrom(fis)) != null) {
      Sha256Hash key = Sha256Hash.of(transaction.getRawData().toByteArray());
      transactionsMap.computeIfAbsent(key, k -> new LongAdder())
          .increment();
    }

    transactionsMap.entrySet().stream().filter(t ->
        t.getValue().longValue() > 1
    ).forEach( t -> {
        System.out.println(t.getKey() + ":" + t.getValue());
    });

    System.out.println("map size:" + transactionsMap.size());
  }
}

class ShowDataArgs {

  @Getter
  @Parameter(names = {
      "--datafile"}, description = "Data file", required = true, validateWith = StringValidator.class)
  private String dataFile;
}