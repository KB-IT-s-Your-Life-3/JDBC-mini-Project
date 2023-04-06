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
	public void buyAsset(Customer c, int assetId) throws SQLException, RecordNotFoundException, AlreadyDealedException, InvalidMoneyException {
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT price FROM asset WHERE asset_id=? AND is_dealed=0";
			ps1 = conn.prepareStatement(query);
			ps1.setInt(1, assetId);
			rs = ps1.executeQuery();
			if(rs.next()) {
				long price = rs.getLong("price");
				if(price <= c.getMoney()) {
					Deal deal = getDeal(assetId, conn);
					
					query = "UPDATE asset SET is_dealed=1 WHERE asset_id=?";
					ps2 = conn.prepareStatement(query);
					ps2.setInt(1, assetId);
					int row = ps2.executeUpdate();
					
					c.setMoney(c.getMoney()-price);
					updateCustomer(c);
					
					if(deal!=null) 	updateDeal(deal, c.getCustId(), conn);
					else 			createDeal(c.getCustId(), assetId, price, conn);
					
					System.out.println(row + "개의 건물의 판매 등록을 취소하였습니다.");
				}
				else throw new InvalidMoneyException("잔액이 부족합니다.");
			} else throw new AlreadyDealedException("존재하지 않는 매물입니다.");

		} finally {
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
	
	public ArrayList<Asset> getAssets(Customer c) throws SQLException, RecordNotFoundException {
		ArrayList<Asset> arr = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnect();
			String query = "SELECT a.asset_id, r.gu, r.dong, a.asset_name, a.created_at, a.price, a.area, a.constructed_year, a.is_dealed "
					+ "FROM deal d, asset a, region r "
					+ "WHERE d.cust_id=? AND d.asset_id = a.asset_id AND a.dong_id = r.dong_id";
			ps = conn.prepareStatement(query);
			ps.setInt(1, c.getCustId());
			rs = ps.executeQuery();
			while(rs.next()) {
				arr.add(new Asset(rs.getString("asset_name"), rs.getString("gu"), rs.getString("dong"), rs.getLong("price"), rs.getDouble("area"), rs.getInt("constructed_year"), rs.getInt("is_dealed")));
			};
		} finally {
			closeAll(conn, ps, rs);
		}
		return arr;
	}
	
	// address 와 같은 지역의 (구/ 동) 구매가능 한 매물 찾기
	@Override
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
	// 전체 매물 중, 동별로 구매가능한 매물 개수 구하기
	public ArrayList<AssetTable> getAssetsCount() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = null;
		ArrayList<AssetTable> arrTAsset = new ArrayList<>();
		try {
			conn = getConnect();
			query = "SELECT a.gu, a.dong, Enabled_buying "
					+ "FROM REGION a, "
					+ "( SELECT dong_id, COUNT(dong_id) Enabled_buying FROM ASSET "
					+ "WHERE is_dealed = 0 "
					+ "GROUP BY dong_id "
					+ ") b "
					+ "WHERE a.dong_id = b.dong_id "
					+ "ORDER BY a.gu";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				arrTAsset.add(new AssetTable(rs.getString("gu"), rs.getString("dong"), rs.getInt("Enabled_buying")));
			}
			
		} finally {
			closeAll(conn, ps, rs);
		}
		return arrTAsset;
	}
	
	
	@Override
	// 지역별 매물 가격 평균을 구마다 동별로 순위를 매긴다.
	public ArrayList<AssetTable> getAvgAssetsPrice() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AssetTable> arrTAsset = new ArrayList<>();
		try {
			conn = getConnect();
			String query = "SELECT a.gu, a.dong, Avg_Price, RANK() OVER (PARTITION BY a.gu ORDER BY Avg_Price DESC) RANK "
					+ "FROM REGION a, "
					+ "( SELECT dong_id, ROUND(AVG(price)) Avg_Price "
					+ "FROM ASSET "
					+ "WHERE is_dealed = 0 "
					+ "GROUP BY dong_id "
					+ ") b "
					+ "WHERE a.dong_id = b.dong_id "
					+ "ORDER BY a.gu";
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				arrTAsset.add(new AssetTable(rs.getString("gu"), rs.getString("dong"), rs.getInt("RANK")));
			}
		} finally {
			closeAll(conn, ps, rs);
		}
		return arrTAsset;
	}
	
	
	
	@Override
	public ArrayList<Asset> getAllAssets() throws SQLException {
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Asset> assets = new ArrayList<>();
        try {
            conn = getConnect();
            String query = "SELECT DONG_ID, ASSET_NAME, CREATED_AT, PRICE, AREA, CONSTRUCTED_YEAR, ASSET_ID, IS_DEALED FROM ASSET";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()) {
                System.out.println("==============");
                assets.add(new Asset(rs.getInt("DONG_ID"),
                      rs.getString("ASSET_NAME"), 
                      rs.getString("CREATED_AT"),
                      rs.getInt("PRICE"),
                      rs.getFloat("AREA"),
                      rs.getInt("CONSTRUCTED_YEAR"),
                      rs.getInt("ASSET_ID"),
                      rs.getInt("IS_DEALED")));
            }
        }finally {
            closeAll(conn, ps, rs);
        }
        return assets;
	}
  
}
