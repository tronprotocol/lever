package org.tron.task;

import com.google.protobuf.ByteString;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.tron.api.GrpcAPI.Return.response_code;
import org.tron.common.config.Args;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.TransactionUtils;
import org.tron.common.utils.Utils;
import org.tron.core.exception.CancelException;
import org.tron.core.exception.CipherException;
import org.tron.protos.Contract;
import org.tron.protos.Protocol.Account;
import org.tron.protos.Protocol.Transaction;
import org.tron.service.WalletGrpcClient;

@Slf4j
public class CreateAssetTask implements Task {

  private Args args;
  private String name;
  private long totalSupply;
  private int trxNum;
  private int icoNum;
  private long startTime;
  private long endTime;
  private String description;
  private String url;
  private long freeAssetNetLimit;
  private long publicFreeNetLimit;
  private String accountAddress;
  private Map<String, String> frozenSupply = new HashMap<>();
  private int voteScore;
  private WalletGrpcClient client;

  public CreateAssetTask(){
    logger.info("Create task: {}.", getClass().getSimpleName());
  }


  @Override
  public void init(Args args) {
    logger.info("Init create asset task.");
    this.args = args;

    String grpcHost = this.args.getGRpcCheckAddress().get(0);
    client = new WalletGrpcClient(
        grpcHost);

    this.name = "pressure1";
    String totalSupplyStr = "100000000000";
    String trxNumStr = "1";
    String icoNumStr = "1";
    this.startTime = (System.currentTimeMillis() + 864000000);
    this.endTime = (System.currentTimeMillis() + 865000000);
    this.description = "TEST";
    this.url = "www.tronscan.org";
    String freeNetLimitPerAccount = "1";
    String publicFreeNetLimitString = "1";
    this.accountAddress = "27meR2d4HodFPYX2V8YRDrLuFpYdbLvBEWi";
    String amount = "1";
    String days = "1";
    this.frozenSupply.put(days, amount);

    this.totalSupply = new Long(totalSupplyStr);
    this.trxNum = new Integer(trxNumStr);
    this.icoNum = new Integer(icoNumStr);

    this.freeAssetNetLimit = new Long(freeNetLimitPerAccount);
    this.publicFreeNetLimit = new Long(publicFreeNetLimitString);
    this.voteScore = 0;
  }

  @Override
  public void start() {
    logger.info("Start create asset task.");

    Transaction transaction = assetIssue();
    transaction = TransactionUtils.signTransaction(transaction, ECKey.fromPrivate(ByteArray.fromHexString("cbe57d98134c118ed0d219c0c8bc4154372c02c1e13b5cce30dd22ecd7bed19e")));

    client.broadcastTransactionRetry(transaction);

    try {
      Thread.sleep(10 * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void shutdown() {
    logger.info("Shutdown create asset task.");
    try {
      client.shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public Transaction assetIssue() {
    Contract.AssetIssueContract.Builder builder = Contract.AssetIssueContract.newBuilder();
    builder.setOwnerAddress(ByteString.copyFrom(Base58.decodeFromBase58Check(this.accountAddress)));
    builder.setName(ByteString.copyFrom(this.name.getBytes()));
    if (this.totalSupply <= 0) {
      return null;
    }
    builder.setTotalSupply(this.totalSupply);
    if (this.trxNum <= 0) {
      return null;
    }
    builder.setTrxNum(this.trxNum);
    if (this.icoNum <= 0) {
      return null;
    }
    builder.setNum(this.icoNum);
    long now = System.currentTimeMillis();
    if (this.startTime <= now) {
      return null;
    }
    if (this.endTime <= this.startTime) {
      return null;
    }
    if (this.freeAssetNetLimit < 0) {
      return null;
    }
    if (this.publicFreeNetLimit < 0) {
      return null;
    }

    builder.setStartTime(this.startTime);
    builder.setEndTime(this.endTime);
    builder.setVoteScore(this.voteScore);
    builder.setDescription(ByteString.copyFrom(this.description.getBytes()));
    builder.setUrl(ByteString.copyFrom(this.url.getBytes()));
    builder.setFreeAssetNetLimit(this.freeAssetNetLimit);
    builder.setPublicFreeAssetNetLimit(this.publicFreeNetLimit);

    for (String daysStr : this.frozenSupply.keySet()) {
      String amountStr = this.frozenSupply.get(daysStr);
      long amount = Long.parseLong(amountStr);
      long days = Long.parseLong(daysStr);
      Contract.AssetIssueContract.FrozenSupply.Builder frozenSupplyBuilder
          = Contract.AssetIssueContract.FrozenSupply.newBuilder();
      frozenSupplyBuilder.setFrozenAmount(amount);
      frozenSupplyBuilder.setFrozenDays(days);
      builder.addFrozenSupply(frozenSupplyBuilder.build());
    }

    return client.createAssetIssue(builder.build());
  }
}
