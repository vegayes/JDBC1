package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCEx {
	
	public static void main(String[] args) {
		
		// 객체 만들기
		
		Connection con  = null;
		
		Statement stmt = null;
		
		ResultSet rs = null;
		
		
		// 생성
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			
			String user = "kh";
			String pw = "kh1234";
			
			con = DriverManager.getConnection(type+ip+port+sid, user, pw);
			
			String sql = "SELECT EMP_ID, EMP_NAME, SALARY, HIRE_DATE FROM EMPLOYEE";
			
			stmt = con.createStatement();

			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				String empId = rs.getString("EMP_ID");

				
				System.out.printf("사번 : %s \n"
						, empId);
				
				
			}
		}catch(ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 경로가 잘못 작성되었습니다.");
		}catch(SQLException e) {
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
