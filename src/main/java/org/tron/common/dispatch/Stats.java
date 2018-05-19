package org.tron.common.dispatch;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.poi.ss.formula.functions.T;
import org.tron.protos.Protocol;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"amount", "nice"})
public class Stats {
  private ContractType type;
  private ByteString address;
  private Long amount;
  private boolean nice;

  public static List<String> result(List<Stats> stats) {
    List<String> result = new ArrayList<>();
    // trx type
    result.add("trx types:" + stats.stream()
        .map(Stats::getType)
        .distinct()
        .collect(Collectors.toList())
    );

    // trx succeed
    Map<ContractType, Long> typeSucceedMap = stats.stream()
        .filter(Stats::isNice)
        .collect(Collectors.groupingBy(Stats::getType, Collectors.counting()));
    result.add("trx succeed:" + typeSucceedMap);

    // trx address succeed
    Map<Stats, List<Stats>> addressSucceed = stats.stream()
        .filter(Stats::isNice)
        .collect(Collectors.groupingBy(Function.identity()));

    Map<Stats, Long> addressAmountMap = addressSucceed.entrySet().stream()
        .map(e ->
          Maps.immutableEntry(e.getKey(), e.getValue().stream()
              .map(Stats::getAmount)
              .reduce(operate(e.getKey()))
              .orElse(0L)
          )
        )
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    result.add("trx address succeed:" + addressAmountMap);
    return result;
  }

  private static BinaryOperator<Long> operate(Stats stats) {
    switch (stats.getType()) {
      case TransferContract: {
        return (l1, l2) -> l1 + l2;
      }
      case VoteAssetContract:
      case VoteWitnessContract: {
        return (l1, l2) -> l2;
      }

      default:
        return (l1, l2) -> 0L;
    }
  }
}
