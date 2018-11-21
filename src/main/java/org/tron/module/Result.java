package org.tron.module;

import org.tron.protos.Protocol.Transaction.Contract.ContractType;

public class Result {

  private String id;
  private ContractType type;
  private long fee;
  private long energyUsageTotal;
  private long energyFee;
  private long energyUsage;
  private long netFee;
  private long netUsage;
  private boolean isSuccess;

  public ContractType getType() {
    return type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setType(ContractType type) {
    this.type = type;
  }

  public long getEnergyUsage() {
    return energyUsage;
  }

  public void setEnergyUsage(long energyUsage) {
    this.energyUsage = energyUsage;
  }

  public long getFee() {
    return fee;
  }

  public void setFee(long fee) {
    this.fee = fee;
  }

  public long getEnergyUsageTotal() {
    return energyUsageTotal;
  }

  public void setEnergyUsageTotal(long energyUsageTotal) {
    this.energyUsageTotal = energyUsageTotal;
  }

  public long getEnergyFee() {
    return energyFee;
  }

  public void setEnergyFee(long energyFee) {
    this.energyFee = energyFee;
  }

  public long getNetFee() {
    return netFee;
  }

  public void setNetFee(long netFee) {
    this.netFee = netFee;
  }

  public long getNetUsage() {
    return netUsage;
  }

  public void setNetUsage(long netUsage) {
    this.netUsage = netUsage;
  }

  public boolean isSuccess() {
    return isSuccess;
  }

  public void setSuccess(boolean success) {
    isSuccess = success;
  }
}