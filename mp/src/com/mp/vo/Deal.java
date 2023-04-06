package com.mp.vo;
import java.time.LocalDateTime;

public class Deal {
  int dealId, custId, assetId;
  long price;
  LocalDateTime dealedAT;

  public Deal() {};

  public int getAssetId() {
    return assetId;
  }

  public void setAssetId(int assetId) {
    this.assetId = assetId;
  }

  public int getCustId() {
    return custId;
  }
  
  public void setCustId(int custId) {
    this.custId = custId;
  }

  public int getDealId() {
    return dealId;
  }

  public void setDealId(int dealId) {
    this.dealId = dealId;
  }

  public LocalDateTime getDealedAT() {
    return dealedAT;
  }

  public void setDealedAT(LocalDateTime dealedAT) {
    this.dealedAT = dealedAT;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  

}
