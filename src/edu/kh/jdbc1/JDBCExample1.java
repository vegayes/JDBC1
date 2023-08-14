package edu.kh.jdbc1;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExample1 {

	public static void main(String[] args) {
		
		// JDBC : Java에서 DB에 연결할 수 있게 해주는 Java Programming API (Java에 기본으로 내장된 인터페이스)
		// java.sql 패키지에서 제공
		
		// * JDBC를 이용한 애플리케이션을 만들때 필요한 것! 
		// 1. Java의 JDBC관련 인터페이스
		// 2. DBMS(Oracle) 
		// 3. Oracle에서 Java 애플리케이션과 연결할 때 사용할 JDBC를 상속받아 구현한 클래스 모음 (ojdbc11.jar 라이브러리)
		//    --> OracleDrive.class 이용 ( JDBC 드라이버 )
		
		
		// 1 단계 : JDBC 객체 참조 변수 선언 (java.sql 패키지의 인터페이스) -- 3가지 
		
		Connection con = null;
		// DB 연결 정보를 담은 객체 
	    // -> DBMS 타입, 이름, IP주소, Port, 계정명, 비밀번호 저장
		// -> DBeaver의 계정 접속 방법과 유사함.
		// * Java와 DB 사이를 연결해주는 통로 == IO할 때 Stream과 비슷 
		
		
		Statement stmt = null; // 주의! 꼭 java.sql에 있는 인터페이스 import 
		// == 셔틀버스 
		// Connection을 통해 SQL문을 DB에 전달하여 실행하고 
		// 생성된 결과(ResultSet, 성공한 행의 개수)를 반환(Java)하는데 사용되는 객체 
		
		
		ResultSet rs = null; 
		// SELECT 질의 성공시 반환되는데 
		// 조회 결과 집합을 나타내는 객체 (SELECT인 경우만 씀.)  
		
		
		try {
		// 2단계 : 참조변수에 알맞은 객체 대입
		
		// 1. DB 연결에 필요한 Oracle JDBC Driver 메모리에 로드해야 함!
		// 런타임 시점에 해당 경로의 클래스를 동적으로 로드함. 
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
	
		//                   패키지 명  +  클래스 명 
		// -> ()안에 작성된 클래스의 객체를 반환 
		// -> 메모리에 객체가 생성되어지고 JDBC 필요 시 알아서 참조하여 사용
		// --> 생략해도 자동으로 메모리 로드가 진행됨(명시적으로 작성하는 것을 권장)
		
		// --> 클래스 로더가 읽은 후 자동으로 인스턴스 만듦. --> DriverManager에 등록해야 함. 
		// --> Connection 인터페이스 구현객체를 생성하는데, Connection 안에 getConnection()을 만듦. 
		// --> getConnection(DBMS 타입, 이름, IP주소, Port, 계정명, 비밀번호 저장) 전달. 
		// --> con으로 넣음?
		// ==> 필요할때마다 꺼내씀.
		
		
		// 2. 연결정보를 담은 Connection을 생성 
		// -> DriverManager 객체를 이용해서 Connection 객체를 만들어 얻어옴!
		
		String type = "jdbc:oracle:thin:@"; // JDBC 드라이버 종류
		String ip = "localhost"; // DB 서버 컴퓨터 IP
								// == 127.0.0.1 (loop back ip) ==> 내 컴퓨터를 가리킴
		String port = ":1521"; // 포트번호 
							  // 1521 (기본값)
		
		String sid = ":XE"; // DB 이름
		String user = "kh"; // 사용자 계정
		String pw = "kh1234"; // 비밀번호

		//getConnection(연결정보);
		
		// DriverManager : 
		// 메모리에 로드된 JDBC 드라이버를 이용해서 
		// Connection 객체를 만드는 역할. 
		// Class.forName을 읽었다.?
		con = DriverManager.getConnection(type + ip + port + sid, user, pw);
								//   url		
		// url--> jdbc:oracle:thin:@localhost:1521:XE
		// --> Connection 만들어줌. 그러므로 Conneciton 참조변수에 대입함. 
		
		//========> JAVA랑 DB랑 통로가 만들어짐
		
		
		// 중간확인
		System.out.println(con); 
		
		
		// 3. SQL 작성 (=> 보낼 값 : 승객)
		// ** JAVA 에서 작성되는 SQL은 마지막에 ';'을 찍으면 안된다!! ==> 오류남 ** 
		String sql = "SELECT EMP_ID, EMP_NAME, SALARY, HIRE_DATE FROM EMPLOYEE";
		
		// 4. Statment 객체 생성
		// -> Connection 객체를 통해서 생성
		stmt = con.createStatement();
	
		// 5. 생성된 Statement객체에 
		// sql을 적재하여 실행한 후 결과를 반환받아와서 rs (Result Set)변수에 저장
		rs = stmt.executeQuery(sql); // 쿼리 실어서 보내고 결과값 다시 반환함. 
		// executeQuery() : SELECT문 수행 메서드, ResultSet반환
		
		
		
		
		
		
		// 3단계 : SQL을 수행해서 반환 받은 결과(ResultSet)를 
		//         한 행씩 접근해서 컬럼값 얻어오기
			while(rs.next()) {
				// rs.next() : rs가 참조하고 있는 ResultSet 객체의 
				//             첫 번째 컬럼부터 순서대로 한 행씩 이동하며
				//             다음 행이 있을 경우 true 없으면 false 반환
				
				// 컬럼 값을 얻어와야 함.
				// rs.get자료형("컬럼명")
				String empId = rs.getString("EMP_ID");
				// 현재 행의 "EMP_ID"(VARCHAR2) 문자열 컬럼의 값을 얻어옴
				
				String empName = rs.getString("EMP_NAME");
				
				int salary = rs.getInt("SALARY");
				
				// java.sql.Date 
				Date hireDate = rs.getDate("HIRE_DATE");
				
				System.out.printf("사번 : %s / 이름 : %s / 급여 : %d / 입사일 : %s\n"
						, empId, empName, salary, hireDate.toString() );
				// java.sql.Date의 toString() 은 yyyy-mm-dd형식으로 오버라이딩 되어있음.
			}

		}catch(ClassNotFoundException e) {
			System.out.println("JDBC 드라이버 경로가 잘못 작성되었습니다.");
			// 오타나면 발생.
		}catch(SQLException e) {
			//SQLException : DB 관련 최상위 예외
			e.printStackTrace();
		}finally {
			// 4 단계 : 사용한 JDBC 객체 자원 반환 (close())
			// ResultSet, Statement, connection 생성역순으로 닫는것을 권장
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(con != null)	con.close(); // 이 친구를 먼저하면 기억된 캐쉬를 닫는다? (Connection을 기억하는 것이 닫힌다.)
			}catch(SQLException e){
				e.printStackTrace();
			}			
		}		
	}
	
}
