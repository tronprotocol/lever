package org.tron.program;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.protobuf.ByteString;
import java.util.HashMap;
import lombok.Getter;
import org.tron.Validator.StringValidator;
import org.tron.api.GrpcAPI.Return;
import org.tron.api.GrpcAPI.TransactionExtention;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.protos.Contract.AssetIssueContract;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletGrpcClient;

public class CreateAsset {

  private static WalletGrpcClient client;

  public static void main(String[] args) {
    Args argsObj = new Args();
    JCommander.newBuilder().addObject(argsObj).build().parse(args);

    client = new WalletGrpcClient(argsObj.getGRPC());

    createAssetIssue(
        argsObj.getPrivateKey(),
        argsObj.getName(),
        argsObj.getTotalSupply(),
        argsObj.getTrxNum(),
        argsObj.getIcoNum(),
        argsObj.getStartTime(),
        argsObj.getEndTime(),
        argsObj.getVoteScore(),
        argsObj.getDescription(),
        argsObj.getUrl(),
        argsObj.getFreeNetLimit(),
        argsObj.getPublicFreeNetLimit(),
        argsObj.getFrozenSupply()
        );
  }

  private static boolean createAssetIssue(String privateKey, String name, long totalSupply, int trxNum, int icoNum,
      long startTime, long endTime, int voteScore, String description, String url,
      long freeNetLimit, long publicFreeNetLimit, HashMap<String, String> frozenSupply) {
    AssetIssueContract.Builder builder = AssetIssueContract.newBuilder();

    ECKey ecKey = ECKey.fromPrivate(ByteArray.fromHexString(privateKey));

    builder.setOwnerAddress(ByteString.copyFrom(ecKey.getAddress()));
    builder.setName(ByteString.copyFrom(name.getBytes()));
    if (totalSupply <= 0) {
      System.out.println("totalSupply <= 0");
      return false;
    }
    builder.setTotalSupply(totalSupply);
    if (trxNum <= 0) {
      System.out.println("trxNum <= 0");
      return false;
    }
    builder.setTrxNum(trxNum);
    if (icoNum <= 0) {
      System.out.println("icoNum <= 0");
      return false;
    }
    builder.setNum(icoNum);
    long now = System.currentTimeMillis();
    if (startTime <= now) {
      System.out.println("startTime <= now");
      return false;
    }
    if (endTime <= startTime) {
      System.out.println("endTime <= startTime");
      return false;
    }
    if (freeNetLimit < 0) {
      System.out.println("freeNetLimit < 0");
      return false;
    }
    if (publicFreeNetLimit < 0) {
      System.out.println("publicFreeNetLimit < 0");
      return false;
    }

    builder.setStartTime(startTime);
    builder.setEndTime(endTime);
    builder.setVoteScore(voteScore);
    builder.setDescription(ByteString.copyFrom(description.getBytes()));
    builder.setUrl(ByteString.copyFrom(url.getBytes()));
    builder.setFreeAssetNetLimit(freeNetLimit);
    builder.setPublicFreeAssetNetLimit(publicFreeNetLimit);

    for (String daysStr : frozenSupply.keySet()) {
      String amountStr = frozenSupply.get(daysStr);
      long amount = Long.parseLong(amountStr);
      long days = Long.parseLong(daysStr);
      AssetIssueContract.FrozenSupply.Builder frozenSupplyBuilder
          = AssetIssueContract.FrozenSupply.newBuilder();
      frozenSupplyBuilder.setFrozenAmount(amount);
      frozenSupplyBuilder.setFrozenDays(days);
      builder.addFrozenSupply(frozenSupplyBuilder.build());
    }

    TransactionExtention transactionExtention = client.createAssetIssue2(builder.build());
    return processTransactionExtention(transactionExtention, ecKey);
  }

  private static boolean processTransactionExtention(TransactionExtention transactionExtention, ECKey ecKey) {
    if (transactionExtention == null) {
      System.out.println("null transactionExtention");
      return false;
    }
    Return ret = transactionExtention.getResult();
    if (!ret.getResult()) {
      System.out.println("Code = " + ret.getCode());
      System.out.println("Message = " + ret.getMessage().toStringUtf8());
      return false;
    }
    Transaction transaction = transactionExtention.getTransaction();
    if (transaction == null || transaction.getRawData().getContractCount() == 0) {
      System.out.println("Transaction is empty");
      return false;
    }
    System.out.println("create asset success");
    transaction = TransactionUtils.signTransaction(transaction, ecKey);
    return client.broadcastTransaction(transaction);
  }

  private static class Args {
    @Getter
    @Parameter(names = {
        "--grpc"}, description = "gRPC host", required = true, validateWith = StringValidator.class)
    private String gRPC = "";

    @Getter
    @Parameter(names = {
        "--private-key"}, required = true)
    private String privateKey = "";

    @Getter
    @Parameter(names = {
        "--name"}, required = true)
    private String name = "";

    @Getter
    @Parameter(names = {
        "--total-supply"}, required = true)
    private long totalSupply = 0L;

    @Getter
    @Parameter(names = {
        "--trx-num"}, required = true)
    private int trxNum = 0;

    @Getter
    @Parameter(names = {
        "--ico-num"}, required = true)
    private int icoNum = 0;

    @Getter
    @Parameter(names = {
        "--start-time"}, required = true)
    private long startTime = 0L;

    @Getter
    @Parameter(names = {
        "--end-time"}, required = true)
    private long endTime = 0L;

    @Getter
    @Parameter(names = {
        "--vote-score"}, required = true)
    private int voteScore = 0;

    @Getter
    @Parameter(names = {
        "--description"}, required = true)
    private String description = "";

    @Getter
    @Parameter(names = {
        "--url"}, required = true)
    private String url = "";

    @Getter
    @Parameter(names = {
        "--free-net-limit"}, required = true)
    private long freeNetLimit = 0L;

    @Getter
    @Parameter(names = {
        "--public-free-net-limit"}, required = true)
    private long publicFreeNetLimit = 0L;


    @Getter
    @DynamicParameter(names = {
        "--frozen-supply"}, required = true)
    private HashMap<String, String> frozenSupply = new HashMap<>();
  }
}

