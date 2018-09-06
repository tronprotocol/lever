package org.tron.program;

import com.google.protobuf.ByteString;
import java.util.Objects;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.tron.common.config.Args;
import org.tron.common.crypto.Hash;
import org.tron.common.utils.Base58;

@Slf4j
public class Lever {

  @Getter
  private static ByteString contractAddress;

  // 生成交易：需要accounts.txt，contract address，交易数量，交易类型
  // 发送交易：需要节点地址，TPS
  public static void main(String[] args) {
    Args.getInstance(args);
    init();
  }

  public static void init() {
    if (Args.getInstance().getContractAddress() != null) {
      contractAddress = ByteString.copyFrom(Objects
          .requireNonNull(Base58.decodeFromBase58Check(Args.getInstance().getContractAddress())));
    }

    if (!Args.getInstance().getNetType().equalsIgnoreCase("testnet")) {
      Hash.changeAddressPrefixMainnet();
    }
  }
}
