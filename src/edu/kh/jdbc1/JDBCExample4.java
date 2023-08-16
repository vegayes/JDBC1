package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc1.model.vo.Employee;

public class JDBCExample4 {
	
	// 직급명, 급여를 입력 받아
	// 해당 직급에서 입력 받은 급여보다 많이 받는 사원의
	// 이름, 직급명, 급여, 연봉을 조회하여 출력
	
	// 단, 조회 결과가 없으면 "조회결과 없음" 출력
	
	// 조회 결과가 있으면 출력
	
	// Employee(empName,jobName, salary, annualIncome)

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			
			System.out.print("직급명을 입력해 주세요 :");
			String inputJob = sc.nextLine();
			
			System.out.print("급여를 입력해 주세요 :");
			int inputSalary = sc.nextInt();
			
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			String type = "jdbc:oracle:thin:@";
			String ip = "localhost";
			String port = ":1521";
			String sid = ":XE";
				
			String user = "kh";
			String pass = "kh1234";
			
			con = DriverManager.getConnection(type+ip+port+sid , user, pass);
			
			String sql = "SELECT EMP_NAME, JOB_NAME, SALARY , SALARY *12 AS ANNUAL_INCOME"
					+ " FROM EMPLOYEE "
					+ " LEFT JOIN JOB USING(JOB_CODE)"
					+ " WHERE JOB_NAME = '" + inputJob + "' AND SALARY > " + inputSalary;
			
			stmt = con.createStatement();
			
			rs = stmt.executeQuery(sql);
			
			List<Employee> list = new ArrayList<Employee>();
			
			while(rs.next()) {
				
				String empName = rs.getString("EMP_NAME");
				String jobName = rs.getString("JOB_NAME");
				int salary = rs.getInt("SALARY");
				int annualIncome= rs.getInt("ANNUAL_INCOME");

				list.add(new Employee(empName, jobName, salary, annualIncome));
			
			}
			
		if(list.isEmpty()) {
			System.out.println("조회 결과 없음");
			
		}else {
			for(Employee tmp : list) {
				System.out.println(tmp);
			}
		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				
				if(rs!= null) rs.close();
				if(stmt!= null) stmt.close();
				if(con!= null) con.close();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}	
}
