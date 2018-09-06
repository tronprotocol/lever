package org.tron.model;

public class Account {
  private String address;
  private String privateKey;

  public Account(String address, String privateKey) {
    this.address = address;
    this.privateKey = privateKey;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPrivateKey() {
    return privateKey;
  }

  public void setPrivateKey(String privateKey) {
    this.privateKey = privateKey;
  }
}
