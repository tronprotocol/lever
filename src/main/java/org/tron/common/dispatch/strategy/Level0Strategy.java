package org.tron.common.dispatch.strategy;


import org.tron.common.dispatch.Stats;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Level0Strategy extends AbstractStrategy<Level1Strategy> {
  @Override
  public List<Stats> stats() {
    return source.stream()
        .map(IStrategy::stats)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

}
