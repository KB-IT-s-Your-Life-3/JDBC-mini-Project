package com.mp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mp.vo.Customer;
import com.mp.vo.Deal;
import com.mp.exception.AlreadyDealedException;
import com.mp.exception.InvalidMoneyException;
import com.mp.exception.RecordNotFoundException;
import com.mp.vo.Asset;
import com.mp.vo.AssetTable;

public interface DAOTemplete {
	// ==================================== Global ====================================
	Connection getConnect() throws SQLException;
	void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException;
	void closeAll(Connection conn, PreparedStatement ps) throws SQLException;

	
	// ==================================== Customer ====================================
	void addCustomer(Customer c) throws SQLException, RecordNotFoundException;

	Customer getCustomer(int custId) throws SQLException, RecordNotFoundException;

	ArrayList<Customer> getAllCustomer() throws SQLException;

	void deleteCustomer(int id) throws SQLException, RecordNotFoundException;

	void updateCustomer(Customer c) throws SQLException, RecordNotFoundException, InvalidMoneyException;

	
	// ==================================== Deal ====================================
	ArrayList<Deal> getPortfolio(int custId) throws SQLException;

	void buyAsset(Customer c, int assetId)
			throws SQLException, RecordNotFoundException, AlreadyDealedException, InvalidMoneyException;

	void createDeal(int custId, int assetId, long dealedMoney, Connection conn) throws SQLException;
	
	void updateDeal(Deal deal, int custId, Connection conn)
			throws SQLException, RecordNotFoundException, InvalidMoneyException;
	
	Deal getDeal(int assetId, Connection conn) throws SQLException;
			
	void enrollAsset(int custId, int assetId) throws SQLException, RecordNotFoundException;

	void cancelEnrollAsset(int custId, int assetId) throws SQLException, RecordNotFoundException;
	
	
	// ==================================== Asset ====================================
	void addAsset(Asset a) throws SQLException;

	ArrayList<Asset> getAssets(Customer c) throws SQLException, RecordNotFoundException;

	ArrayList<Asset> getAssets(String address) throws SQLException, RecordNotFoundException;

	ArrayList<AssetTable> getAssetsCount() throws SQLException;

	ArrayList<AssetTable> getAvgAssetsPrice() throws SQLException;

	ArrayList<Asset> getAllAssets() throws SQLException;

	boolean isDealed(int assetId, Connection conn) throws SQLException, RecordNotFoundException;

}
