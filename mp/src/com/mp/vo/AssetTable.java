package com.mp.vo;

public class AssetTable {
  private int num;
  private String gu, dong;

  public AssetTable() {};
  

  public AssetTable( String gu, String dong, int num) {
	this.num = num;
	this.gu = gu;
	this.dong = dong;
}


public String getDong() {
    return dong;
  }

  public void setDong(String dong) {
    this.dong = dong;
  }

  public String getGu() {
    return gu;
  }

  public void setGu(String gu) {
    this.gu = gu;
  }

  public int getNum() {
    return num;
  }

  public void setNum(int num) {
    this.num = num;
  }


@Override
public String toString() {
	return "AssetTable [gu=" + gu + ", dong=" + dong + ", num=" + num +"]";
}
  
  
  
}
