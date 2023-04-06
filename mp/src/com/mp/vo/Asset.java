package com.mp.vo;
public class Asset {
  
	private int assetId, constructedYear, isDealed, dongId;
	private String assetName, gu, dong, createdAt, regionCode;
	private double area;
	private long price;
	
	public Asset(String assetName, String gu, String dong, long price, double area, int constructedYear, int isDealed) {
		super();
		this.constructedYear = constructedYear;
		this.assetName = assetName;
		this.gu = gu;
		this.dong = dong;
		this.createdAt = createdAt;
		this.area = area;
		this.price = price;
		this.isDealed = isDealed;
	}
  
  public Asset(int dongId, String assetName, String createdAt, int price, float area, int constructedYear, int assetId, int isDealed) {
	this.dongId = dongId;  
    this.assetName = assetName;
    this.createdAt = createdAt;
    this.price = price;
    this.area = area;
    this.constructedYear = constructedYear;
    this.assetId = assetId;
    this.isDealed = isDealed;
  }
  
  public Asset(String regionCode, String assetName, long price, double area, int constructedYear) {
	super();
	this.constructedYear = constructedYear;
	this.assetName = assetName;
	this.area = area;
	this.price = price;
	this.regionCode = regionCode;
  }
  
  public Asset(int regionCode, int assetId, String assetName, long price, double area, String gu, String dong) {
	super();
	this.regionCode = String.valueOf(regionCode);
	this.assetId = assetId;
	this.assetName = assetName;
	this.price = price;
	this.area = area;
	this.gu = gu;
	this.dong = dong;
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
    return createdAt;
  }
  
  public void setCereatedAt(String cereatedAt) {
    this.createdAt = cereatedAt;
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

  public double getArea() {
    return area;
  }

  public void setArea(float area) {
    this.area = area;
  }
  
  public String getregionCode() {
	  return regionCode;
  }

@Override
public String toString() {
	return "Asset [assetId=" + assetId + ", constructedYear=" + constructedYear + ", assetName=" + assetName
			+ ", gu=" + gu + ", dong=" + dong + ", cereatedAt=" + createdAt + ", regionCode="
			+ regionCode + ", price=" + price + ", isDealed=" + isDealed + ", area=" + area + "]";
}
  
  

}
