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

import static org.tron.common.dispatch.creator.contract.TriggerContractTransactionCreator.triggerCallContract;

import com.google.protobuf.ByteString;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import org.spongycastle.util.encoders.Hex;
import org.tron.api.GrpcAPI.TransactionExtention;
import org.tron.common.utils.AbiUtil;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.module.Account;
import org.tron.protos.Contract.TriggerSmartContract;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletClient;

public class CheckAirdrop {
  public static List<Account> accounts = new ArrayList<>();

  public static void main(String[] args) {
    WalletClient walletClient = new WalletClient();
    walletClient.init(0);

    // 读取accounts
    File ff = new File("accounts.txt");
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(ff);
      ObjectInputStream ois = new ObjectInputStream(fis);

      while (fis.available() > 0) {
        Account account = (Account) ois.readObject();
        accounts.add(account);
      }

      for (int i = 0; i < accounts.size(); i++) {
        Account account = accounts.get(i);
        String addressBase58 = Base58.encode58Check(ByteArray.fromHexString(account.getAddress()));

        String airDropMArgsStr = "\""+ addressBase58 +"\"";

        TriggerSmartContract contract = triggerCallContract(ByteString.copyFrom(Base58.decodeFromBase58Check("27meR2d4HodFPYX2V8YRDrLuFpYdbLvBEWi")).toByteArray(), Base58.decodeFromBase58Check("27UscVhqkUcCmZzzG1UQthRdiAtY4X4LiUD"), 0, org.bouncycastle.util.encoders.Hex
            .decode(AbiUtil.parseMethod("getBalance(address)", airDropMArgsStr, false)));

        TransactionExtention transactionExtention = walletClient.triggerContract(contract);

        if (transactionExtention == null || !transactionExtention.getResult().getResult()) {
          System.out.println("RPC create call trx failed!");
          System.out.println("Code = " + transactionExtention.getResult().getCode());
          System.out
              .println("Message = " + transactionExtention.getResult().getMessage().toStringUtf8());
          continue;
        }

        Transaction transaction = transactionExtention.getTransaction();
        if (transaction.getRetCount() != 0 &&
            transactionExtention.getConstantResult(0) != null &&
            transactionExtention.getResult() != null) {
          byte[] result = transactionExtention.getConstantResult(0).toByteArray();
          System.out.println("message:" + transaction.getRet(0).getRet());
          System.out.println(":" + ByteArray
              .toStr(transactionExtention.getResult().getMessage().toByteArray()));
          System.out.println("Result:" + Hex.toHexString(result));
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
