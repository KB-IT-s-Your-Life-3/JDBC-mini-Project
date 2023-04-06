package com.mp.test;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mp.dao.DAO;
import com.mp.exception.RecordNotFoundException;
import com.mp.vo.Asset;

import config.ServerInfo;

public class AssetTradeTest {
  public static void main(String[] args) throws RecordNotFoundException {
    DAO db = DAO.getInstance();
    
    System.out.println("=====gilyeon test======");
//    System.err.println("---addAsset(Asset asset)---");
//    try {
//		db.addAsset(new Asset("1144010200", "반포 자이", 837220, 50.8, 2022));
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
    
    System.err.println("---getAssets(String address)---");
    try {
    	ArrayList<Asset> arrAsset = db.getAssets("구로동");
		for(Asset a : arrAsset)System.out.println(a);
	} catch (SQLException e) {
		e.printStackTrace();
	}
    try {
    	ArrayList<Asset> arrAsset = db.getAssets("강남구");
		for(Asset a : arrAsset)System.out.println(a);
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
