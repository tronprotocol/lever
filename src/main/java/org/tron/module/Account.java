package org.tron.module;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account implements Serializable {

  private String privateKey;

  private String address;

  public Account() {

  }

  public Account(String privateKey, String address) {
    this.privateKey = privateKey;
    this.address = address;
  }
}
