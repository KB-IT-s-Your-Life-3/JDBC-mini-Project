package com.mp.test;


import java.util.ArrayList;

import com.mp.dao.DAO;
import com.mp.exception.RecordNotFoundException;
import com.mp.vo.Asset;
import com.mp.vo.AssetTable;
import com.mp.vo.Customer;
import com.mp.vo.Deal;

import config.ServerInfo;

public class AssetTradeTest {
  public static void main(String[] args) throws RecordNotFoundException {
    DAO db = DAO.getInstance();
    ArrayList<Asset> arrAsset = new ArrayList<>();
    ArrayList<AssetTable> arrTAsset = new ArrayList<>();
    
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
//    	ArrayList<Asset> arr = db.getAssets(db.getCustomer(2));
//    	for(Asset a : arr)	System.out.println(a);
    	
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
//    	
//        System.out.println("=====gilyeon test======");
//      System.err.println("---addAsset(Asset asset)---");
//  	db.addAsset(new Asset("1144010200", "반포 자이", 837220, 50.8, 2022));
      
      
//      System.err.println("---getAssets(String address)---");
//      arrAsset = db.getAssets("구로동");
//      for(Asset a : arrAsset)System.out.println(a);
//  	  arrAsset = db.getAssets("구로구");
//  	  for(Asset a : arrAsset)System.out.println(a);
//  	  arrAsset = db.getAssets("대한민국");
//  	  for(Asset a : arrAsset)System.out.println(a);
      
      
//      System.err.println("---getAssetsCount()---");
//      arrTAsset = db.getAssetsCount();
//      for(AssetTable a : arrTAsset)System.out.println(a);
        
//      System.err.println("---getAvgAssetsPrice()---");
//    	arrTAsset = db.getAvgAssetsPrice();
//    	for(AssetTable a : arrTAsset)System.out.println(a);
    	
    	ArrayList<Asset> arr = db.getAllAssets();
    	for(Asset a : arr) 	System.out.println(a);
    	
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
