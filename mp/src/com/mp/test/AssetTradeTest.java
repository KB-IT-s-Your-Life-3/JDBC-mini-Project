package com.mp.test;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mp.dao.DAO;
import com.mp.exception.RecordNotFoundException;
import com.mp.vo.Asset;
import com.mp.vo.AssetTable;

import config.ServerInfo;

public class AssetTradeTest {
  public static void main(String[] args) throws RecordNotFoundException {
    DAO db = DAO.getInstance();
    ArrayList<Asset> arrAsset = new ArrayList<>();
    ArrayList<AssetTable> arrTAsset = new ArrayList<>();
    
    
    System.out.println("=====gilyeon test======");
//    System.err.println("---addAsset(Asset asset)---");
//    try {
//		db.addAsset(new Asset("1144010200", "반포 자이", 837220, 50.8, 2022));
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
    
    
//    System.err.println("---getAssets(String address)---");
//    try {
//    	arrAsset = db.getAssets("구로동");
//		for(Asset a : arrAsset)System.out.println(a);
//		
//		arrAsset = db.getAssets("구로구");
//		for(Asset a : arrAsset)System.out.println(a);
//		
//		arrAsset = db.getAssets("대한민국");
//		for(Asset a : arrAsset)System.out.println(a);
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
    
    
//    System.err.println("---getAssetsCount()---");
//    try {
//    	arrTAsset = db.getAssetsCount();
//    	for(AssetTable a : arrTAsset)System.out.println(a);
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
    
    
    System.err.println("---getAvgAssetsPrice()---");
    try {
    	arrTAsset = db.getAvgAssetsPrice();
    	for(AssetTable a : arrTAsset)System.out.println(a);
	} catch (SQLException e) {
		e.printStackTrace();
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
