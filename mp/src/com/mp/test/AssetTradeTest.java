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
//			System.err.println("========= addCustomer =========");
//			db.addCustomer(new Customer("115010901", "차민혁", 1050001, 0));
//			
//			System.err.println("========= getAllCustomer =========");
//			ArrayList<Customer> list = db.getAllCustomer();
//			for (Customer c : list)
//				System.out.println(c);
//
//			
//			System.err.println("========= getCustomer =========");
//			Customer c = db.getCustomer(1);
//			System.out.println(c);
//
//			
//			System.err.println("========= deleteCustomer=========");
//			db.deleteCustomer(5);
//
//			
//			System.err.println("========= updateCustomer =========");
//			db.updateCustomer(new Customer(3, "115010901", "차민혁", 1050001, 0));
//
//			
//			
//			
//			
//			System.err.println("========= getAssets =========");
//			arrAsset = db.getAssets(db.getCustomer(41));
//			for (Asset a : arrAsset)
//				System.out.println(a);
//			
//			
//			System.err.println("========= buyAsset =========");
//			db.buyAsset(db.getCustomer(41), 205);
//
//			
//			System.err.println("========= getPortfolio =========");
//			ArrayList<Deal> arr = db.getPortfolio(1);
//			for (Deal d : arr)
//				System.out.println(d.getDealId());
//
//			
//			System.err.println("========= enrollAsset =========");
//			db.enrollAsset(2, 1);
//
//			
//			System.err.println("========= cancelEnrollAsset=========");
//			db.cancelEnrollAsset(1, 2);
//
//			
//			
//
//			
//			System.err.println("========= addAsset =========");
//			db.addAsset(new Asset("1144010200", "반포 자이", 837220, 50.8, 2022));
//
//			
//			System.err.println("========= getAssets =========");
//			arrAsset = db.getAssets("구로동");
//			for (Asset a : arrAsset)
//				System.out.println(a);
//			arrAsset = db.getAssets("구로구");
//			for (Asset a : arrAsset)
//				System.out.println(a);
//			arrAsset = db.getAssets("대한민국");
//			for (Asset a : arrAsset)
//				System.out.println(a);
//
//			
//			System.err.println("========= getAssetsCount=========");
//			arrTAsset = db.getAssetsCount();
//			for (AssetTable a : arrTAsset)
//				System.out.println(a);
//
//			
//			System.err.println("========= getAvgAssetsPrice =========");
//			arrTAsset = db.getAvgAssetsPrice();
//			for (AssetTable a : arrTAsset)
//				System.out.println(a);
//
//			System.err.println("========= getAllAssets =========");
//			arrAsset = db.getAllAssets();
//			for (Asset a : arrAsset)
//				System.out.println(a);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	static {
		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("드라이버 로딩 설공..");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패..");
		}
	}

}
