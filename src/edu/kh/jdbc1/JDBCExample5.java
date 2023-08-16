package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee;

public class JDBCExample5 {

	// 입사일을 입력("2022-09-06") 받아
	// 입력 받은 값보다 먼저 입사한 사람의
	// 이름, 입사일, 성별(M,F) 조회
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		int count  = 1;
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			System.out.print("입사일을 입력해주세요:");
			String inputHire = sc.nextLine();
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";			
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
			
			String user = "kh";
			String pw = "kh1234";
			
			con = DriverManager.getConnection(type+ip+port+sid, user,pw);
			
			String sql = "SELECT EMP_NAME, TO_CHAR(HIRE_DATE,'yyyy\"년 \"mm\"월 \"dd\"일\"') HIRE_DATE,"
					+ " DECODE(SUBSTR(EMP_NO, 8, 1) ,'1', 'M','2','F') AS GENDER"
					+ " FROM EMPLOYEE "
					+ " WHERE TO_CHAR(HIRE_DATE,'yyyy-mm-dd') < '"+ inputHire +"'";
			
			/* 한글 별칭
			String sql = "SELECT EMP_NAME 이름, TO_CHAR(HIRE_DATE,'yyyy\"년 \"mm\"월 \"dd\"일\"') 입사일,"
					+ " DECODE(SUBSTR(EMP_NO, 8, 1) ,'1', 'M','2','F') 성별"
					+ " FROM EMPLOYEE "
					+ " WHERE HIRE_DATE < TO_DATE('"+ inputHire +"')";
			*/
			
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			
			List<Employee> arr = new ArrayList<Employee>();
			
			while(rs.next()) {
/* 선생님 풀이 :: 매개변수 생성자 다시 만들지 않고 setter 이용				
				Employee emp = new Employee(); // 기본 생성자로 Employee 객체 생성
	
				emp.setEmpName(rs.getString("이름")); // 조회 시 컬럼명이 "이름"
				emp.setHireDate(rs.getString("입사일")); 
				emp.setGender(rs.getString("성별").charAt(0)); 
				// JAVA의 char : 문자 1개 의미
				// DB의 CHAR : 고정길이 문자열(== String)
				
				
*/
				String empName = rs.getString("EMP_NAME");
				String hireDate = rs.getString("HIRE_DATE");
				char gender = rs.getString("GENDER").charAt(0);

				arr.add(new Employee(empName,hireDate,gender));
			}
			
			if(arr.isEmpty()) {
				System.out.println("조회불가");
			}else { 
	/* 선생님 풀이
				for(int i = 0 ; i < arr.size(); i++) {
					System.out.printf("%02d) %s / %s / %c \n", i+1, 
							arr.get(i).getEmpName(),							
							arr.get(i).getHireDate(),
							arr.get(i).getGender() );
							
				}
				
	*/
				
				for(Employee tmp : arr) {
					
					System.out.print(count+") ");
					
					System.out.println(tmp);
						count ++;
				
				
				
				}				
			}

		}catch(Exception e) {
			
			e.printStackTrace();
		}finally {
			
			try{
				
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null) con.close();				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
	}	
}
