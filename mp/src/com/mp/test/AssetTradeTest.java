package com.mp.test;

import java.util.ArrayList;

import com.mp.dao.DAO;
import com.mp.vo.Customer;

import config.ServerInfo;

public class AssetTradeTest {
  public static void main(String[] args) {
    DAO db = DAO.getInstance();
    
	System.out.println("\n===============전체 고객 출력=====================\n");
    try {
		ArrayList<Customer> list=db.getAllCustomer();
		for(Customer c : list) System.out.println(c);
	}catch(Exception e) {
		
	}
//    
//    try {
//		Customer c = db.getCustomer(1); 
//		System.out.println(c);
//	}catch(Exception e) {
//		
//	}
    
//    try {
//    	db.deleteCustomer(5);
//    }catch(Exception e) {
//		
//	}
    
    
//    try {
//    	db.updateCustomer(new Customer(3, "차민혁", "115010901", 1050001, 0));
//    }catch(Exception e) {
//		
//	}
    
    try {
    	db.addCustomer(new Customer("차민혁", "115010901", 1050001, 0));
    }catch(Exception e) {
		
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
