package org.tron.program;

import com.beust.jcommander.JCommander;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import lombok.Data;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Sha256Hash;
import org.tron.protos.Protocol;
import org.tron.service.WalletClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class CheckBlock {
  static Multimap<String, BlockInfo> trxIds = ArrayListMultimap.create();
  static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

  public static void main(String[] args) {
    SendCoinArgs args1 = new SendCoinArgs();
    JCommander.newBuilder().addObject(args1).build().parse(args);

    WalletClient walletClient = new WalletClient("06BCCD3C89BC855368152FFBE4829502E6482D69783E206DA6529E5849B2313F");
    walletClient.init(0);

    // 124666-125776
    // 131780-131870
    // 132117 - 132208
    // 132264 - 132353
//    service.scheduleAtFixedRate(() -> {
//    }, 5, 5, TimeUnit.SECONDS);

    LongStream.rangeClosed(56193, 132353).forEach(i -> {
      try {
        Protocol.Block block = walletClient.getBlock(i);
        block.getTransactionsList().forEach(trx -> {
          String trxId = getTransactionId(trx);
          if (i % 20 == 0) {
            System.out.println("trxid:" + trxId + ", blocknum:" + i);
          }
          trxIds.put(trxId, new BlockInfo(block));
        });
      } catch (Exception e) {

      }
    });
//    String prefix = "\n**************check witness result:\n";
    Map<String, Long> countMap = trxIds.asMap().values().stream()
        .filter(c -> c.size() > 1)
        .map(HashSet::new)
        .flatMap(Collection::stream)
        .map(BlockInfo::getWitnessAddress)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//    String result = countMap.entrySet().stream()
//        .map(e -> "address:" + e.getKey() + ", count:" + e.getValue())
//        .collect(Collectors.joining("\n"));
//    System.err.println(prefix + result);

    System.out.println("\n**************check witness result:\n");
    countMap.forEach((k, v) -> System.out.println("address:" + k + ", count:" + v));

    System.out.println("\n**************check trx result:\n");
    trxIds.asMap().entrySet().stream().filter(e -> e.getValue().size() > 1).forEach(e -> System.out.println(
        "trxid:" + e.getKey()
        + ", block:" + e.getValue()
    ));

  }

  public static String getTransactionId(Protocol.Transaction transaction) {
    return Sha256Hash.of(transaction.getRawData().toByteArray()).toString();
  }

  @Data
  public static class BlockInfo {
    String witnessAddress;
    long blockNum;

    public BlockInfo(Protocol.Block block) {
      this.witnessAddress = Base58.encode58Check(block.getBlockHeader().getRawData().getWitnessAddress().toByteArray());
      this.blockNum = block.getBlockHeader().getRawData().getNumber();
    }

    @Override
    public String toString() {
      return "BlockInfo{" +
          "witnessAddress='" + witnessAddress + '\'' +
          ", blockNum=" + blockNum +
          '}';
    }
  }
}
