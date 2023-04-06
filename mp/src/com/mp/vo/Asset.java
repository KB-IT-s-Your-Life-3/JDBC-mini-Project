package com.mp.vo;
public class Asset {
  public Asset(int assetId, int constructedYear, String assetName, String gu, String dong, String cereatedAt,
			float size, long price, boolean isDealed) {
		super();
		this.assetId = assetId;
		this.constructedYear = constructedYear;
		this.assetName = assetName;
		this.gu = gu;
		this.dong = dong;
		this.cereatedAt = cereatedAt;
		this.size = size;
		this.price = price;
		this.isDealed = isDealed;
	}

private int assetId, constructedYear;
  private String assetName, gu, dong, cereatedAt;
  private float size;
  private long price;
  private boolean isDealed;

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

  public float getSize() {
    return size;
  }

  public void setSize(float size) {
    this.size = size;
  }

}
