package com.mp.vo;
import java.util.ArrayList;

public class Customer {
  int custId;
  String custName, address;
  long money;
  boolean isDeleted;
  ArrayList<Deal> dealArr;

  public Customer() {};
  
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

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
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

  public boolean getIsDeleted() {
    return isDeleted;
  }

  public void setIsDeleted(boolean isDeleted) {
    this.isDeleted = isDeleted;
  }
}
