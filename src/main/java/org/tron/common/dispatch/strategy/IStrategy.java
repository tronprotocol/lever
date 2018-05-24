package org.tron.common.dispatch.strategy;

import org.tron.common.dispatch.Stats;

import java.util.List;

public interface IStrategy<T> {

  T dispatch();

  List<Stats> stats();
}
