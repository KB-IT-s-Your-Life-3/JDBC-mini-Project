package com.mp.vo;
public class Asset {
  private int assetId, constructedYear;
  private String assetName, address, gu, dong, cereatedAt, regionCode;
  private long price;
  private boolean isDealed;
  private double area;
  
  

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
  
  public String getregionCode() {
	  return regionCode;
  }

@Override
public String toString() {
	return "Asset [assetId=" + assetId + ", constructedYear=" + constructedYear + ", assetName=" + assetName
			+ ", address=" + address + ", gu=" + gu + ", dong=" + dong + ", cereatedAt=" + cereatedAt + ", regionCode="
			+ regionCode + ", price=" + price + ", isDealed=" + isDealed + ", area=" + area + "]";
}
  
  

}
