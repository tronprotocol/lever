package org.tron.common.dispatch;

import com.google.common.base.Charsets;
import com.google.protobuf.ByteString;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.tron.common.dispatch.strategy.Level2Strategy;
import org.tron.common.utils.Base58;
import org.tron.service.WalletClient;

@Getter
public abstract class AbstractTransactionCreator extends Level2Strategy {
  protected String privateKey = "cbe57d98134c118ed0d219c0c8bc4154372c02c1e13b5cce30dd22ecd7bed19e";
  protected ByteString ownerAddress = ByteString.copyFrom(Base58.decodeFromBase58Check("27meR2d4HodFPYX2V8YRDrLuFpYdbLvBEWi"));
  protected ByteString toAddress = ByteString.copyFrom(Base58.decodeFromBase58Check("27ZESitosJfKouTBrGg6Nk5yEjnJHXMbkZp"));
  protected Long amount = 1L;
  protected ByteString assetName = ByteString.copyFrom("pressure1", Charsets.UTF_8);
  @Autowired
  protected WalletClient client;
}
