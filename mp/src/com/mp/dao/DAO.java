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
		ResultSet rs = null;
		try {
			String[] addr = c.getAddress().split(" ");
			conn = getConnect();
			String query = "SELECT dong_id FROM region WHERE gu=? AND dong=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, addr[0]);
			ps.setString(2, addr[1]);
			rs = ps.executeQuery();
			if(rs.next()) {
				String dong_id = rs.getString("dong_id");
				query = "INSERT INTO CUSTOMER (CUST_ID, DONG_ID, CUST_NAME, MONEY, IS_DELETED) VALUES (seq_customer.nextVal , ?, ?, ?, ?)";
				ps = conn.prepareStatement(query);
				ps.setString(1, dong_id);
				ps.setString(2, c.getCustName());
				ps.setLong(3, c.getMoney());
				ps.setInt(4, c.getIsDeleted() ? 1 :0);
				int row = ps.executeUpdate();
				System.out.println(row + " Customer created");
			} else {
				throw new RecordNotFoundException("존재하지 않는 지역명입니다.");
			}
		} finally {
			closeAll(conn,ps, rs);
		}
	}
	
	@Override
	public Customer getCustomer(String id) throws SQLException, RecordNotFoundException {
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
	public ArrayList<Customer> getAllCustomer() throws SQLException {
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
	public void deleteCustomer(int id) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
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
	public void addAsset(Asset asset) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnect();
			String query = "INSERT INTO ASSET (ASSET_ID, DONG_ID, ASSET_NAME, CREATED_AT, PRICE, AREA, CONSTRUCTED_YEAR) VALUES (SEQ_ASSET.nextVal, ?, ?, SYSDATE, ?, ?, ?)";
			ps = conn.prepareStatement(query);
			ps.setLong(1, Long.parseLong(asset.getregionCode()));
			ps.setString(2, asset.getAssetName());
			ps.setLong(3, asset.getPrice());
			ps.setDouble(4, asset.getArea());
			ps.setInt(5, asset.getConstructedYear());
			int row = ps.executeUpdate();
			System.out.println(row + " Asset is Created !");
		} finally {
			closeAll(conn,ps);
		}
	}
	
	@Override
	// address 와 같은 지역의 (구/ 동) 구매가능 한 매물 찾기
	public ArrayList<Asset> getAssets(String address) throws SQLException, RecordNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		String regionName = null;
		ArrayList<Asset> arrAsset = new ArrayList<>();
		
		if(address.charAt(address.length() - 1) != '구' && address.charAt(address.length() - 1) != '동') {
			throw new RecordNotFoundException("'구'/'동'단위로 입력해주세요.");
		}
	
		try {
			conn = getConnect();
			query = "SELECT r.dong_id, a.asset_id, a.asset_name, a.price, a.area, r.gu, r.dong FROM ASSET a, REGION r  WHERE (r.gu = ? OR r.dong= ?) AND a.dong_id = r.dong_id AND is_dealed = 0";
			ps = conn.prepareStatement(query);
			ps.setString(1, address);
			ps.setString(2, address);
			rs = ps.executeQuery();
			while(rs.next()) {
				arrAsset.add(new Asset(rs.getInt("dong_id"), rs.getInt("asset_id"), rs.getString("asset_name"), rs.getLong("price"), rs.getDouble("area"), rs.getString("gu"), rs.getString("dong") ));
			}
		} finally {
			closeAll(conn, ps, rs);
		}
		
		return arrAsset;
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

	@Override
	public ArrayList<Asset> getAssets(int custId) throws SQLException, RecordNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}  
  
}
