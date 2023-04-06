package com.mp.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mp.exception.AlreadyDealedException;
import com.mp.exception.InvalidMoneyException;
import com.mp.exception.RecordNotFoundException;
import com.mp.vo.Asset;
import com.mp.vo.AssetTable;
import com.mp.vo.Customer;
import com.mp.vo.Deal;

import config.ServerInfo;

public class DAO implements DAOTemplete{
	private static DAO service = new DAO();

	private DAO() {};

	public static DAO getInstance() {
		return service;
	}

  	@Override
  	public Connection getConnect() throws SQLException {
		Connection conn=DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASS);
		System.out.println("DB Server Connection......");
		return conn;
	}
	
	@Override
	public void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
		if(conn!=null)	conn.close();
		if(ps!=null)	ps.close();
		if(rs!=null)	rs.close();
	}
	
	@Override
	public void closeAll(Connection conn, PreparedStatement ps) throws SQLException {
		this.closeAll(conn, ps, null);		
	}
	
	@Override
	public boolean isDealed(int assetId, Connection conn) throws SQLException, RecordNotFoundException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		conn = getConnect();
		String query = "SELECT asset_id FROM asset WHERE asset_id=? AND is_dealed=0";
		ps = conn.prepareStatement(query);
		ps.setInt(1, assetId);
		
		rs = ps.executeQuery();
		return rs.next();
	}
	
	@Override
	public void addCustomer(Customer c) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "INSERT INTO CUSTOMER (CUST_ID, DONG_ID, CUST_NAME, MONEY, IS_DELETED) VALUES (seq_customer.nextVal , ?, ?, ?, ?)";
			ps = conn.prepareStatement(query);
			
			ps.setString(1, c.getDongId());
			ps.setString(2, c.getCustName());
			ps.setLong(3, c.getMoney());
			ps.setInt(4, c.getIsDeleted());
			System.out.println(" Customer created");
			
		} finally {
			closeAll(conn,ps);
		}
	}
	
	@Override
	public Customer getCustomer(int custId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Customer customer = null;
		try {
			conn = getConnect();
			String query = "SELECT CUST_ID, DONG_ID, CUST_NAME, MONEY, IS_DELETED FROM CUSTOMER WHERE CUST_ID =?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, custId);
			rs = ps.executeQuery();
			if(rs.next()) {
				customer = new Customer(custId, 
										rs.getString("DONG_ID"), 
										rs.getString("CUST_NAME"),
										rs.getLong("MONEY"),
										rs.getInt("IS_DELETED"));
			}
			
		} finally {
			closeAll(conn, ps, rs);
		}
		return customer;
	}
	
	@Override
	public ArrayList<Customer> getAllCustomer() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Customer> customers = new ArrayList<>();
		try {
			conn = getConnect();
			String query = "SELECT CUST_ID, DONG_ID, CUST_NAME, MONEY, IS_DELETED FROM CUSTOMER";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				customers.add(new Customer(rs.getInt("CUST_ID"), 
						rs.getString("DONG_ID"), 
						rs.getString("CUST_NAME"),
						rs.getLong("MONEY"),
						rs.getInt("IS_DELETED")));
			}
		} finally {
			closeAll(conn, ps, rs);
		}
		return customers;
	}
	
	@Override
	public void deleteCustomer(int custId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "DELETE customer WHERE cust_id = ?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, custId);
			System.out.println(ps.executeUpdate() + " row DELETE OK");
		} finally {
			closeAll(conn,ps);
		}
	}
	
	@Override
	public void updateCustomer(Customer c) throws SQLException, RecordNotFoundException, InvalidMoneyException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "UPDATE customer SET DONG_ID = ?, CUST_NAME = ?, MONEY = ?, IS_DELETED =? WHERE CUSTOMER";
			ps = conn.prepareStatement(query);
			ps.setString(1, c.getDongId());
			ps.setString(2, c.getCustName());
			ps.setString(3, c.getCustName());
			ps.setLong(4, c.getMoney());
			System.out.println("업뎃 완료");
		} finally {
			closeAll(conn,ps);
		}
	}
	
	@Override
	public ArrayList<Deal> getPortfolio(int id) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
		} finally {
			closeAll(conn, ps, rs);
		}
		return null;
	}
	
	@Override
	public void buyAsset(int custId, int assetId) throws SQLException, RecordNotFoundException, AlreadyDealedException, InvalidMoneyException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
		} finally {
			closeAll(conn,ps);
		}
	}
	
	@Override
	public void enrollAsset(int custId, int assetId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
		} finally {
			closeAll(conn,ps);
		}
	}
	
	@Override
	public void cancelEnrollAsset(int custId, int assetId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
		} finally {
			closeAll(conn,ps);
		}
	}
	
	@Override
	public void addAsset(Asset a) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
		} finally {
			closeAll(conn,ps);
		}
	}
	
	@Override
	public ArrayList<Asset> getAssets(int custId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
		} finally {
			closeAll(conn, ps, rs);
		}
		return null;
	}
	
	@Override
	public ArrayList<AssetTable> getAssetsCount() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
		} finally {
			closeAll(conn, ps, rs);
		}
		return null;
	}
	
	@Override
	public ArrayList<AssetTable> getAvgAssetsPrice() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
		} finally {
			closeAll(conn, ps, rs);
		}
		return null;
	}
	
	@Override
	public ArrayList<AssetTable> getAllAssets() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
		} finally {
			closeAll(conn, ps, rs);
		}
		return null;
	}  
  
}
