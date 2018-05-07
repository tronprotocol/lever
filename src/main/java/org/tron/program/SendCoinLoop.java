/*
 * java-tron is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * java-tron is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.tron.program;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.tron.common.utils.Base58;
import org.tron.service.WalletClient;

public class SendCoinLoop {
  private static final String PRIVATE_KEY = "cbe57d98134c118ed0d219c0c8bc4154372c02c1e13b5cce30dd22ecd7bed19e";
  private static final int THREAD_COUNT = 4;

  private static List<WalletClient> walletClients = new ArrayList<>();

  public static void main(String[] args) {
    if (args.length < 2) {
      return;
    }

    long runSeconds = new Long(args[0]);
    double tps = new Double(args[1]);

    walletClients = IntStream.range(0, THREAD_COUNT).mapToObj(i -> {
      WalletClient walletClient = new WalletClient(PRIVATE_KEY);
      walletClient.init();
      return walletClient;
    })
    .collect(Collectors.toList());

    rateLimiter(runSeconds, tps);
  }

  public static void rateLimiter(long runSeconds, double tps) {
    ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(THREAD_COUNT));
    CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
    Long startTimeMs = new Long(System.currentTimeMillis());
    RateLimiter limiter = RateLimiter.create(tps);

    long runMs = runSeconds * 1000;
    for (int i = 0; i < THREAD_COUNT; ++i) {
      executorService.execute(new Task(walletClients.get(i % THREAD_COUNT), runMs, limiter, startTimeMs));
      latch.countDown();
    }

    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      executorService.shutdown();
    }
  }
}

class Task implements Runnable {
  private static final byte[] TO_ADDRESS = Base58.decodeFromBase58Check("27ZESitosJfKouTBrGg6Nk5yEjnJHXMbkZp");
  private static final Long AMOUNT = 1000000L;
  private static LongAdder trueCount = new LongAdder();
  private static LongAdder falseCount = new LongAdder();
  private static LongAdder currentCount = new LongAdder();
  private static ConcurrentHashMap<Long, LongAdder> resultMap = new ConcurrentHashMap<>();
  public static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
  private WalletClient walletClient;
  private RateLimiter limiter;
  private long runMs;
  private Long startTimeMs;

  static {
    service.scheduleAtFixedRate(() -> {
      System.out.println(
          "current: " + currentCount.longValue()
              + ", true: " + trueCount.longValue()
              + ", false: " + falseCount.longValue()
              + ", timestamp: " + (System.currentTimeMillis() / 1000)
              + ", map: " + resultMap);
    }, 5, 5, TimeUnit.SECONDS);
  }

  public Task(final WalletClient walletClient, long runMs, RateLimiter limiter, Long startTimeMs) {
    this.walletClient = walletClient;
    this.limiter = limiter;
    this.runMs = runMs;
    this.startTimeMs = startTimeMs;
  }

  @Override
  public void run() {
    try {
      while (System.currentTimeMillis() - this.startTimeMs < this.runMs) {

        limiter.acquire();
        boolean b = walletClient.sendCoin(TO_ADDRESS, AMOUNT);

        if (b) {
          trueCount.increment();
        } else {
          falseCount.increment();
        }

        currentCount.increment();

        long currentMinutes = System.currentTimeMillis() / 1000L / 60;

        resultMap.computeIfAbsent(currentMinutes, k -> new LongAdder()).increment();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}