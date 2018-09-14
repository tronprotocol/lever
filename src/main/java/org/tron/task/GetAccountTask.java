package org.tron.task;

import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.tron.common.config.Args;
import org.tron.common.utils.Base58;
import org.tron.protos.Protocol.Account;
import org.tron.service.WalletGrpcClient;

@Slf4j
public class GetAccountTask implements Task {

  private List<WalletGrpcClient> clients = new ArrayList<>();
  private Map<String, Account> accountMap;
  private Account account;

  private List<String> grpcHosts;
  private String accountAddress;

  public GetAccountTask(List<String> grpcHosts, String accountAddress) {
    logger.info("Create task: {}.", getClass().getSimpleName());

    this.grpcHosts = grpcHosts;
    this.accountAddress = accountAddress;
  }

  @Override
  public void init(Args args) {
    logger.info("Init get account task.");

    accountMap = new HashMap<>();

    initGrpcClients();

    initCheckAccounts();
  }

  private void initGrpcClients() {
    logger.info("Init gRPC clients.");

    for (String host : this.grpcHosts) {
      WalletGrpcClient client = new WalletGrpcClient(
          host);

      clients.add(client);
    }
  }

  private void initCheckAccounts() {
    logger.info("Init check accounts.");

    account = Account.newBuilder()
        .setAddress(ByteString.copyFrom(
            Objects.requireNonNull(
                Base58.decodeFromBase58Check(accountAddress)
            )
        ))
        .build();
  }

  @Override
  public void start() {
    logger.info("Start get account task.");

    for (WalletGrpcClient client : clients) {
      accountMap.put(client.getHost(),
          client.getAccount(account));
    }
  }

  @Override
  public void shutdown() {
    logger.info("Shutdown get account task.");

    for (WalletGrpcClient client : clients) {
      try {
        client.shutdown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public Map<String, Account> getAccountMap() {
    return accountMap;
  }
}
