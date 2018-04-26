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

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import org.tron.common.utils.Base58;
import org.tron.service.WalletClient;

public class SendCoinLoop {
  private static final String PRIVATE_KEY = "cbe57d98134c118ed0d219c0c8bc4154372c02c1e13b5cce30dd22ecd7bed19e";

  private static WalletClient walletClient;

  public static void main(String[] args) {
    if (args.length < 2) {
      return;
    }

    long amount = new Long(args[0]);
    double tps = new Double(args[1]);

    walletClient = new WalletClient(PRIVATE_KEY);
    walletClient.init();

    rateLimiter(walletClient, amount, tps);
  }

  public static void rateLimiter(final WalletClient walletClient, long amount, double tps) {
    ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

    RateLimiter limiter = RateLimiter.create(tps);

    for (int c = 0; c < amount; ++c) {
      limiter.acquire();
      executorService.submit(new Task(walletClient));
    }

  }
}

class Task implements Callable {
  private static final byte[] TO_ADDRESS = Base58.decodeFromBase58Check("27ZESitosJfKouTBrGg6Nk5yEjnJHXMbkZp");
  private static final Long AMOUNT = 1L;
  private static long trueCount = 0;
  private static long falseCount = 0;
  private static long currentCount = 0;

  private WalletClient walletClient;

  public Task(final WalletClient walletClient) {
    this.walletClient = walletClient;
  }

  @Override
  public Object call() throws Exception {
    boolean b = walletClient.sendCoin(TO_ADDRESS, AMOUNT);

    if (b) {
      increaseTrueCount();
    } else {
      increaseFalseCount();
    }

    increaseCurrentCount();

    if (getCurrentCount() % 1000 == 0) {
      System.out.println("current: " + getCurrentCount() + ", true: " + getTrueCount() + ", false: " + getFalseCount() + ", timestamp: " + System.currentTimeMillis() / 1000);
    }

    return b;
  }

  public synchronized void increaseTrueCount() {
    ++trueCount;
  }

  public synchronized void increaseFalseCount() {
    ++falseCount;
  }

  public synchronized void increaseCurrentCount() {
    ++currentCount;
  }

  public synchronized long getTrueCount() {
    return trueCount;
  }

  public synchronized long getFalseCount() {
    return falseCount;
  }

  public synchronized long getCurrentCount() {
    return currentCount;
  }
}