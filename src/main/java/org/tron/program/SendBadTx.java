package org.tron.program;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.apache.commons.csv.CSVRecord;
import org.tron.api.GrpcAPI;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.CsvUtils;
import org.tron.common.utils.Utils;
import org.tron.core.exception.CancelException;
import org.tron.core.exception.CipherException;
import org.tron.keystore.Wallet;
import org.tron.walletserver.WalletClient;

/**
 *   Please comment out :
 *   throw new ValidateSignatureException("miss sig or contract");
 *   in function : public boolean validateSignature()
 *   from TransactionCapsule.java , java-tron
  */

public class SendBadTx {
  public static void main(String[] args) throws CipherException, IOException, CancelException {
    SendBadTxArgs args1 = new SendBadTxArgs();
    JCommander.newBuilder().addObject(args1).build().parse(args);
    List<String> privateKeyList = getStrings(args1.getPrivateKeyFile());
    int privateKeySize = privateKeyList.size();
    if (privateKeySize == 0) {
      System.out.println("private key is empty");
      return;
    }
    byte[] privateKey = ByteArray.fromHexString(privateKeyList.get(0));
    WalletClient walletClient = new WalletClient(privateKey);
    walletClient.init(0);

    ECKey key =  new ECKey(Utils.getRandom());

    for(int i=100_0000; i>0;i--){
      GrpcAPI.Return response = walletClient.sendBadTransactionWithoutSign(key.getAddress(),i);
      System.out.println(response.getResult());
    }
  }
  private static List<String> getStrings(String filePath) {
    List<CSVRecord> read = CsvUtils.read(new File(filePath));
    List<String> stringList = new ArrayList();

    read.forEach(l -> {
      stringList.add(l.get(0));
    });

    return stringList;
  }
}
class SendBadTxArgs {

//  @Getter
//  @Parameter(names = {
//      "--tps"}, description = "tps", required = true)
//  private double tps;


  @Getter
  @Parameter(names = {
      "--privatekeyFile"}, description = "Private key file", required = true)
  private String privateKeyFile;

//
//  @Getter
//  @Parameter(names = {
//      "--amount"}, description = "Drops amount per transaction", required = true)
//  private long amount;
//
//  @Getter
//  @Parameter(names = {
//      "--count"}, description = "Transaction counts", required = true)
//  private long count;
//
//  @Getter
//  @Parameter(names = {
//      "--output"}, description = "Save data file", required = true)
//  private String output;

}