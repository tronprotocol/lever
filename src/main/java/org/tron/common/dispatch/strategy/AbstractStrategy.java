package org.tron.common.dispatch.strategy;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractStrategy<T extends Bucket> extends Bucket implements IStrategy<T> {
  private Random random = new Random(System.currentTimeMillis());
  @Getter
  @Setter
  protected List<T> source = new ArrayList<>();

  @PostConstruct
  public void check() {
    int bucket = source.stream()
        .mapToInt(b -> b.end - b.begin + 1)
        .sum();
    if (bucket != 0 && bucket != 100) {
      throw new IllegalArgumentException("bucket sum not equals 100.");
    }
  }

  @Override
  public T dispatch() {
    int randomInt = random.nextInt(100);
    return source.stream()
        .filter(t -> t.begin <= randomInt && t.end > randomInt)
        .findFirst()
        .orElse(null);
  }

}
