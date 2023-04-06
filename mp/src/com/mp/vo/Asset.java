package com.mp.vo;
public class Asset {
  private int assetId, constructedYear;
  private String assetName, address, gu, dong, cereatedAt;
  private long price, regionCode;
  private boolean isDealed;
  private double area;
  
  

  public Asset(long regionCode, String assetName, long price, double area, int constructedYear) {
	super();
	this.constructedYear = constructedYear;
	this.assetName = assetName;
	this.area = area;
	this.price = price;
	this.regionCode = regionCode;
}

public int getAssetId() {
    return assetId;
  }

  public void setAssetId(int assetId) {
    this.assetId = assetId;
  }

  public String getAssetName() {
    return assetName;
  }

  public void setAssetName(String assetName) {
    this.assetName = assetName;
  }

  public String getCereatedAt() {
    return cereatedAt;
  }
  
  public void setCereatedAt(String cereatedAt) {
    this.cereatedAt = cereatedAt;
  }

  public int getConstructedYear() {
    return constructedYear;
  }

  public void setConstructedYear(int constructedYear) {
    this.constructedYear = constructedYear;
  }

  public boolean getIsDealed() {
    return isDealed;
  }

  public void setIsDealed(boolean isDealed) {
    this.isDealed = isDealed;
  }

  public String getGu() {
    return gu;
  }

  public void setGu(String gu) {
    this.gu = gu;
  }

  public String getDong() {
    return dong;
  }

  public void setDong(String dong) {
    this.dong = dong;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  public double getArea() {
    return area;
  }

  public void setArea(float area) {
    this.area = area;
  }
  
  public String getAddress() {
	    return address;
	  }
  
  public long getregionCode() {
	  return regionCode;
  }

}
