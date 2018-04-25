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

import org.tron.common.utils.Base58;
import org.tron.service.WalletClient;

public class SendCoinLoop {
  private static final byte[] TO_ADDRESS = Base58.decodeFromBase58Check("27ZESitosJfKouTBrGg6Nk5yEjnJHXMbkZp");
  private static final String PRIVATE_KEY = "cbe57d98134c118ed0d219c0c8bc4154372c02c1e13b5cce30dd22ecd7bed19e";
  private static final Long AMOUNT = 1L;

  private static WalletClient walletClient;

  public static void main(String[] args) {
    if (args.length < 1) {
      return;
    }

    walletClient = new WalletClient(PRIVATE_KEY);
    walletClient.init();

    long count = new Long(args[0]);

    long trueCount = 0;
    long falseCount = 0;
    for (long current = 0; current < count; ++current) {
      boolean b = walletClient.sendCoin(TO_ADDRESS, AMOUNT);

      if (b) {
        ++trueCount;
      } else {
        ++falseCount;
      }

      if (current % 100 == 0) {
        System.out.println("current: " + (current + 1) + ", true: " + trueCount + ", false: " + falseCount + ", timestamp: " + System.currentTimeMillis() / 1000);
      }
    }
  }
}
