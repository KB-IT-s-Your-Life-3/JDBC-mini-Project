package com.mp.vo;
import java.util.ArrayList;

public class Customer {
  int custId;
  String custName, dongId;
  long money;
  int isDeleted;
  ArrayList<Deal> dealArr;

  public Customer(int custId, String dongId, String custName, long money, int isDeleted) {
	  this.custId = custId;
	  this.dongId = dongId;
	  this.money = money;
	  this.custName = custName;
	  this.isDeleted = isDeleted;
  };
  
  public Customer(String dongId, String custName, long money, int isDeleted) {
	  this.dongId = dongId;
	  this.money = money;
	  this.custName = custName;
	  this.isDeleted = isDeleted;
  };
  
  @Override
public String toString() {
	return "Customer [custId=" + custId + ", custName=" + custName + ", dongId=" + dongId + ", money=" + money
			+ ", isDeleted=" + isDeleted + ", dealArr=" + dealArr + "]";
}

public int getCustId() {
    return custId;
  }
  
  public void setCustId(int custId) {
    this.custId = custId;
  }

  public String getCustName() {
    return custName;
  }

  public void setCustName(String custName) {
    this.custName = custName;
  }

  public long getMoney() {
    return money;
  }

  public void setMoney(long money) {
    this.money = money;
  }
  
  public ArrayList<Deal> getDealArr() {
    return dealArr;
  }

  public void setDealArr(ArrayList<Deal> dealArr) {
    this.dealArr = dealArr;
  }

  public int getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(int isDeleted) {
    this.isDeleted = isDeleted;
  }

public String getDongId() {
	// TODO Auto-generated method stub
	return dongId;
}
}
