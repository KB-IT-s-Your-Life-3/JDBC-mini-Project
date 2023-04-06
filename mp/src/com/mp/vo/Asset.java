package com.mp.vo;
public class Asset {
  public Asset(String assetName, String gu, String dong, long price, double area, int constructedYear, int isDealed) {
		super();
		this.constructedYear = constructedYear;
		this.assetName = assetName;
		this.gu = gu;
		this.dong = dong;
		this.cereatedAt = cereatedAt;
		this.area = area;
		this.price = price;
		this.isDealed = isDealed;
	}

  private int assetId, constructedYear, isDealed;
  private String assetName, gu, dong, cereatedAt;
  private double area;
  private long price;

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

  public int getIsDealed() {
    return isDealed;
  }

  public void setIsDealed(int isDealed) {
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

  public double getSize() {
    return area;
  }

  public void setSize(double area) {
    this.area = area;
  }

}
