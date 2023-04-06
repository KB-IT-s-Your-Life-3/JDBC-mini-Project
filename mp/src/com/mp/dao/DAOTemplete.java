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
  Connection getConnect() throws SQLException;
  void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException;
  void closeAll(Connection conn, PreparedStatement ps) throws SQLException;
  void addCustomer(Customer c) throws SQLException, RecordNotFoundException;
  Customer getCustomer(String id) throws SQLException, RecordNotFoundException;
  ArrayList<Customer> getAllCustomer() throws SQLException;
  void deleteCustomer(int id) throws SQLException, RecordNotFoundException;
  void updateCustomer(Customer c) throws SQLException, RecordNotFoundException, InvalidMoneyException;
  ArrayList<Deal> getPortfolio(int custId) throws SQLException;
  void buyAsset(int custId, int assetId) throws SQLException, RecordNotFoundException, AlreadyDealedException, InvalidMoneyException;
  void enrollAsset(int custId, int assetId) throws SQLException, RecordNotFoundException;
  void cancelEnrollAsset(int custId, int assetId) throws SQLException, RecordNotFoundException;
  void addAsset(Asset a) throws SQLException;
  ArrayList<Asset> getAssets(int custId) throws SQLException, RecordNotFoundException;
  ArrayList<AssetTable> getAssetsCount() throws SQLException;
  ArrayList<AssetTable> getAvgAssetsPrice() throws SQLException;
  ArrayList<AssetTable> getAllAssets() throws SQLException;
  boolean isDealed(int assetId, Connection conn) throws SQLException, RecordNotFoundException;
Customer getCustomer(int custId) throws SQLException, RecordNotFoundException;
}
