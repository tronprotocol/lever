package org.tron.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ClientPool {
  private List<String> grpcs;
  private ConcurrentHashMap<String, WalletGrpcClient> clients;

  public ClientPool(List<String> grpcs) {
    this.grpcs = grpcs;
    this.clients = new ConcurrentHashMap<>();
  }

  public void connect() {
    for (String grpc : this.grpcs) {
      if (null == this.clients.get(grpc)) {
        this.clients.put(grpc, new WalletGrpcClient(grpc));
      }
    }
  }

  public List<String> getGrpcs() {
    return this.grpcs;
  }

  public WalletGrpcClient getClient(String grpc) {
    return this.clients.get(grpc);
  }

  public void shutdown(String grpc) {
    try {
      this.clients.get(grpc).shutdown();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void shutdownAll() {
    for (String grpc: this.grpcs) {
      shutdown(grpc);
    }
  }
}
