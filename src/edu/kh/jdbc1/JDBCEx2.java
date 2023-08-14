package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCEx2 {
	
	public static void main(String[] args) {
		
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			
			String user = "workbook";
			String pw = "workbook";
			
			con = DriverManager.getConnection(type+ip+port+sid, user, pw);
			
			
			
			stmt = con.createStatement();
			
			String sql = "SELECT * FROM INVENTORY WHERE INVENTORY = 0 OR INVENTORY IS NULL"; 
			
			
			rs = stmt.executeQuery(sql);
		
			while(rs.next()) {
				
				String prId = rs.getString("PR_ID");
				String category = rs.getString("CATEGORY");
				String prName = rs.getString("PR_NAME");
				int inventory = rs.getInt("INVENTORY");
				String account = rs.getString("ACCOUNT");
				int price = rs.getInt("PRICE");
				
				System.out.printf("상품ID : %s / 카테고리 : %s / 상품명 : %s / 재고 : %d \n\t  / 거래처 : %s / 가격 : %d\n",
						prId, category, prName, inventory, account, price);
				
			}
			
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	
		
	}
	
	
	
	
	

}
