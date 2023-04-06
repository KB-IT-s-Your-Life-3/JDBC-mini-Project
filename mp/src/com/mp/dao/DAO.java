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
	public Customer getCustomer(int custId) throws SQLException, RecordNotFoundException {
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT price FROM asset WHERE asset_id=? AND is_dealed=0";
			ps = conn.prepareStatement(query);
			ps.setInt(1, assetId);
			rs = ps.executeQuery();
			if(rs.next()) {
				long price = rs.getLong("price");
				query = "SELECT * FROM custoemr WHERE cust_id=?";
				ps = conn.prepareStatement(query);
				ps.setInt(1,custId);
				rs = ps.executeQuery();
				if(rs.next()) {
					if(price <= rs.getLong("money")) {
						Deal deal = checkDeal(assetId, conn);
						if(deal!=null) 	updateDeal(deal, custId, conn);
						else 			createDeal(custId, assetId, price, conn);
						
						cancelEnrollAsset(custId, assetId);
						System.out.println(" asset is dealed.");
					}
					else throw new InvalidMoneyException("잔액이 부족합니다.");
				} else System.out.println("Something wrong");
			} else throw new AlreadyDealedException("존재하지 않는 매물입니다.");
		} finally {
			closeAll(conn,ps);
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
		System.out.println(row + " deal created");
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
	
	public Deal checkDeal(int assetId, Connection conn) throws SQLException {
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
								rs.getTimestamp("dealted_at").toLocalDateTime()
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
				System.out.println(row + " assets update to sell");
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
				System.out.println(row + " assets update to do not sell");
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
			String query = "SELECT a.asset_name, r.gu, r.dong, a.created_at, a.price, a.area, a.constructed_year, a.is_dealed "
					+ "FROM deal d, asset a, region r "
					+ "WHERE d.cust_id=? AND d.asset_id = a.asset_id AND a.dong_id = r.dong_id";
			ps = conn.prepareStatement(query);
			ps.setInt(1, custId);
			rs = ps.executeQuery();
			while(rs.next()) {
				arr.add(new Asset(rs.getString("asset_name"), rs.getString("gu"), rs.getString("dong"), rs.getLong("price"), rs.getDouble("area"), rs.getInt("constructed_year"), rs.getInt("is_dealed")));
			};
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

	@Override
	public Customer getCustomer(String id) throws SQLException, RecordNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}  
  
}
