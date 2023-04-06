package com.mp.vo;
import java.time.LocalDateTime;

public class Deal {
  int dealId, custId, assetId;
  long price;
  LocalDateTime dealedAt;

  public Deal(int dealId, int custId, int assetId, long price, LocalDateTime dealedAt) {
	super();
	this.dealId = dealId;
	this.custId = custId;
	this.assetId = assetId;
	this.price = price;
	this.dealedAt = dealedAt;
  }
  
  public Deal(int custId, int assetId, long price, LocalDateTime dealedAt) {
	  this(0, custId, assetId, price, dealedAt);
  }

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
    return dealedAt;
  }

  public void setDealedAT(LocalDateTime dealedAT) {
    this.dealedAt = dealedAT;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  

}
