package com.mp.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
		String query = "SELECT asset_id FROM asset WHERE asset_id=? AND is_dealed=1";
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
			String query = "SELECT cust_id, dong_id, cust_name, money, is_deleted FROM customer WHERE CUST_ID=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, custId);
			rs = ps.executeQuery();
			if(rs.next()) {
				customer = new Customer(custId, 
										rs.getString("dong_id"), 
										rs.getString("cust_name"),
										rs.getLong("money"),
										rs.getInt("is_deleted"));
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
		System.out.println("!!!!");
		try {
			conn = getConnect();
			String query = "UPDATE customer SET DONG_ID = ?, CUST_NAME = ?, MONEY = ?, IS_DELETED =? WHERE cust_id=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, c.getDongId());
			ps.setString(2, c.getCustName());
			ps.setLong(3, c.getMoney());
			ps.setInt(4, c.getIsDeleted());
			ps.setInt(5, c.getCustId());
			ps.executeUpdate();
			System.out.println("업뎃 완료");
		} finally {
			closeAll(conn,ps);
		}
	}
	
	@Override
	public ArrayList<Deal> getPortfolio(int custId) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Deal> arr = new ArrayList<Deal>();
		try {
			conn = getConnect();
			String query = "SELECT deal_id, cust_id, asset_id, dealed_money, dealed_at FROM deal WHERE cust_id=? ORDER BY 5";
			ps = conn.prepareStatement(query);
			ps.setInt(1, custId);
			rs = ps.executeQuery();
			while(rs.next()) {
				arr.add(new Deal(
							rs.getInt("deal_id"),
							rs.getInt("cust_id"),
							rs.getInt("asset_id"),
							rs.getLong("dealed_money"),
							rs.getTimestamp("dealed_at").toLocalDateTime()));
			}
			
		} finally {
			closeAll(conn, ps, rs);
		}
		return arr;
	}
	
	@Override
	public void buyAsset(int custId, int assetId) throws SQLException, RecordNotFoundException, AlreadyDealedException, InvalidMoneyException {
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			conn.setAutoCommit(false);
			String query = "SELECT price FROM asset WHERE asset_id=? AND is_dealed=0";
			ps1 = conn.prepareStatement(query);
			ps1.setInt(1, assetId);
			rs = ps1.executeQuery();
			if(rs.next()) {
				long price = rs.getLong("price");
				Customer c = getCustomer(custId);
				if(price <= c.getMoney()) {
					Deal deal = getDeal(assetId, conn);
					if(deal!=null) 	updateDeal(deal, custId, conn);
					else 			createDeal(custId, assetId, price, conn);
					
					query = "UPDATE asset SET is_dealed=1 WHERE asset_id=?";
					ps3 = conn.prepareStatement(query);
					ps3.setInt(1, assetId);
					int row = ps3.executeUpdate();
					
					c.setMoney(c.getMoney()-price);
					updateCustomer(c);
					
					System.out.println(row + "개의 건물의 판매 등록을 취소하였습니다.");
					conn.commit();
				}
				else throw new InvalidMoneyException("잔액이 부족합니다.");
			} else throw new AlreadyDealedException("존재하지 않는 매물입니다.");
			
		} catch(Exception e) {
			conn.rollback();
			throw e;
		} finally {
			conn.setAutoCommit(true);
			closeAll(conn,ps1);
			if(ps2!=null)	ps2.close();
		}
	}
	
	public void createDeal(int custId, int assetId, long dealedMoney, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		String query = "INSERT INTO deal (deal_id, cust_id, asset_id, dealed_money, dealed_at) VALUES (seq_deal.nextVal , ?, ?, ?, SYSDATE)";
		ps = conn.prepareStatement(query);
		ps.setInt(1, custId);
		ps.setInt(2, assetId);
		ps.setLong(3, dealedMoney);
		int row = ps.executeUpdate();
		System.out.println(row + "개의 거래가 체결되었습니다.");
	}
	
	public void updateDeal(Deal deal, int custId, Connection conn) throws SQLException, RecordNotFoundException, InvalidMoneyException {
		PreparedStatement ps = null;
		String query = "UPDATE deal SET cust_id=? WHERE deal_id=?";
		ps = conn.prepareStatement(query);
		ps.setInt(1, custId);
		ps.setInt(2, deal.getDealId());
		int row = ps.executeUpdate();
		System.out.println(row + " deal updated");
		
		Customer owner = getCustomer(deal.getCustId());
		owner.setMoney(owner.getMoney()+deal.getPrice());
		updateCustomer(owner);
	}
	
	public Deal getDeal(int assetId, Connection conn) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Deal deal = null;
		conn = getConnect();
		String query = "SELECT deal_id, cust_id, asset_id, dealed_money, dealed_at FROM deal WHERE asset_id=?";
		ps = conn.prepareStatement(query);
		ps.setInt(1, assetId);
		rs = ps.executeQuery();
		if(rs.next()) deal = new Deal(
			rs.getInt("deal_id"),
			rs.getInt("cust_id"),
			rs.getInt("asset_id"),
			rs.getLong("dealed_money"),
			rs.getTimestamp("dealed_at").toLocalDateTime()
		);
		return deal;
	}
	
	@Override
	public void enrollAsset(int custId, int assetId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT deal_id FROM deal WHERE cust_id=? AND asset_id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, custId);
			ps.setInt(2, assetId);
			rs = ps.executeQuery();
			if(rs.next()) {
				query = "UPDATE asset SET is_dealed=0 WHERE asset_id=?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, assetId);
				int row = ps.executeUpdate();
				System.out.println(row + "개의 건물을 판매 등록하였습니다.");
			} else throw new RecordNotFoundException("해당 부동산을 소유하고 있지 않습니다.");
			
		} finally {
			closeAll(conn, ps, rs);
		}
	}
	
	@Override
	public void cancelEnrollAsset(int custId, int assetId) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT deal_id FROM deal WHERE cust_id=? AND asset_id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, custId);
			ps.setInt(2, assetId);
			rs = ps.executeQuery();
			if(rs.next()) {
				query = "UPDATE asset SET is_dealed=1 WHERE asset_id=?";
				ps = conn.prepareStatement(query);
				ps.setInt(1, assetId);
				int row = ps.executeUpdate();
				System.out.println(row + "개의 건물의 판매 등록을 취소하였습니다.");
			} else throw new RecordNotFoundException("해당 부동산을 소유하고 있지 않습니다.");
			
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
		ArrayList<Asset> arr = new ArrayList<>();
		try {
			conn = getConnect();
			
			String query = "SELECT cust_id FROM customer WHERE cust_id=?";
			ps = conn.prepareStatement(query);
			ps.setInt(1, custId);
			rs = ps.executeQuery();
			if(rs.next()) {
				query = "SELECT a.asset_id, r.gu, r.dong, a.asset_name, a.created_at, a.price, a.area, a.constructed_year, a.is_dealed "
					+ "FROM deal d, asset a, region r "
					+ "WHERE d.cust_id=? AND d.asset_id = a.asset_id AND a.dong_id = r.dong_id";
				ps = conn.prepareStatement(query);
				ps.setInt(1, custId);
				rs = ps.executeQuery();
				while(rs.next()) {
				arr.add(new Asset(rs.getString("asset_name"), rs.getString("gu"), rs.getString("dong"), rs.getLong("price"), rs.getDouble("area"), rs.getInt("constructed_year"), rs.getInt("is_dealed")));
				};
			} else throw new RecordNotFoundException("존재하지 않는 사용자입니다.");
		} finally {
			closeAll(conn, ps, rs);
		}
		return arr;
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
