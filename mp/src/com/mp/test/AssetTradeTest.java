package com.mp.test;

import java.util.ArrayList;

import com.mp.dao.DAO;
import com.mp.vo.Asset;

import config.ServerInfo;

public class AssetTradeTest {
  public static void main(String[] args) {
    DAO db = DAO.getInstance();

    try {
    	// buyAsset;
//    	db.buyAsset(3, 1);
    	
    	// getPortfolio;
//    	ArrayList<Deal> arr = db.getPortfolio(1);
//    	for(Deal d : arr)	System.out.println(d.getDealId());
    	
    	// enrollAsset
//    	db.enrollAsset(2, 1);
    	
    	// cancelEnrollAsset
//    	db.cancelEnrollAsset(1, 2);
    	
    	// getAssets
    	ArrayList<Asset> arr = db.getAssets(db.getCustomer(2));
    	for(Asset a : arr)	System.out.println(a);
    	
    	System.out.println("///////////////////////");
    	
    	// 전체 고객 출력
//    	ArrayList<Customer> list=db.getAllCustomer();
//		for(Customer c : list) System.out.println(c);
    	
    	// getCustomer
//    	Customer c = db.getCustomer(1); 
//		System.out.println(c);
    	
    	// deleteCustomer
//    	db.deleteCustomer(5);
    	
    	// updateCustomer
//    	db.updateCustomer(new Customer(3, "차민혁", "115010901", 1050001, 0));
    	
    	// addCustomer
//    	db.addCustomer(new Customer("차민혁", "115010901", 1050001, 0));
    	
    } catch(Exception e) {
    	System.out.println(e.getMessage());
    }

  }

  static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이버 로딩 설공..");
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패..");
		}
	}
}
