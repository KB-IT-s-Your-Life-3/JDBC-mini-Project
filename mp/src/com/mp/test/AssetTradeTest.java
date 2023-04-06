package com.mp.test;

import com.mp.dao.DAO;

import config.ServerInfo;

public class AssetTradeTest {
  public static void main(String[] args) {
    DAO db = DAO.getInstance();
    
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
