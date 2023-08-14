package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int count = 0;
		
		// 1단계 : JDBC 객체 참조변수 생성(java.sql)
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			// 2단계 : 참조변수에 알맞은 객체 대입
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 연결정보
			String type = "jdbc:oracle:thin:@"; // JDBC 드라이버 종류
			String ip = "localhost"; // DB 서버 컴퓨터 IP
			String port = ":1521"; // 포트번호 
			String sid = ":XE"; // DB 이름
			String user = "kh"; // 사용자 계정
			String pw = "kh1234"; // 비밀번호
			
			
			con = DriverManager.getConnection(type+ip+port+sid , user,pw);
			
			// sql 작성
			System.out.println("<입력 받은 급여보다 많이 받는(초과) 직원만 조회>");
			
			System.out.print(">> 급여 입력 : ");
			int input = sc.nextInt();
			
			// ★★★★★★★★★★★
			String sql = "SELECT EMP_ID, EMP_NAME , SALARY FROM EMPLOYEE WHERE SALARY > "+ input;
			
			// 버스 만들기
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			
		
			// 3단계 : SQL을 수행해서 반환받은 결과(RESULTSET)를 한 행씩 접근해서 컬럼값 얻어오기
			while(rs.next()) {
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d\n" 
									,empId, empName,salary);
				count ++;
			}			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}finally {
				try {
					if(rs != null) rs.close();
					if(stmt != null) stmt.close();
					if(con != null) con.close();
					
					System.out.println("행의 개수 : "+ count);
				}catch(Exception e) {
					e.printStackTrace();
				}
		}	
	}
}
