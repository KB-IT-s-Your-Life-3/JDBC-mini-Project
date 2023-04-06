package com.mp.test;

import java.sql.SQLException;

import com.mp.dao.DAO;
import com.mp.vo.Asset;

import config.ServerInfo;

public class AssetTradeTest {
  public static void main(String[] args) {
    DAO db = DAO.getInstance();
    
    System.out.println("=====gilyeon test======");
    try {
		db.addAsset(new Asset(1144010200, "자이", 837220, 50.8, 2022));
		System.out.println("드라이버 로딩 설공..");
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
