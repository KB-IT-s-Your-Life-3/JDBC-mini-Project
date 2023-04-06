package com.mp.test;

import java.util.ArrayList;

import com.mp.dao.DAO;
import com.mp.vo.Deal;

import config.ServerInfo;

public class AssetTradeTest {
  public static void main(String[] args) {
    DAO db = DAO.getInstance();
    try {
    	// buyAsset;
//    	db.buyAsset(1, 2);
    	
    	// getPortfolio;
//    	ArrayList<Deal> arr = db.getPortfolio(1);
//    	for(Deal d : arr)	System.out.println(d.getDealId());
//    	db.enrollAsset(1, 1);
    	
    	// enrollAsset
//    	db.enrollAsset(1, 2);
    	
    	// cancelEnrollAsset
    	db.cancelEnrollAsset(1, 2);
    	
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
