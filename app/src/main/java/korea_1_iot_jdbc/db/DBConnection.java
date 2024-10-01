package korea_1_iot_jdbc.db;

import java.sql.Connection;	// DB 연결을 위한 인터페이스 클래스
import java.sql.DriverManager;	// DB 드라이버를 관리하는 역할
import java.sql.SQLException;	// SQL 작업 중 발생 가능한 예외 처리를 위해 사용

// DBConnection 클래스
// : DB 연결을 관리하는 역할
public class DBConnection {
	// DB 연결 URL을 상수로 선언: MySQL DB 연결
	// : localhost(로컬 컴퓨터), 3306(MySQL 서버의 기본 포트 번호)
	private static final String URL = "jdbc:mysql://localhost:3306/korea_iot";
	
	// DB 사용자 이름을 상수로 선언
	// : DB 접속 시 사용할 사용자 계정 이름
	private static final String USER = "root";
	
	private static final String PASSWORD = "hongdong2@";
	
	// DB에 연결하여 그 연결을 반환
	// : SQL 관련 에러 발생을 방지 
	public static Connection getConnection() throws SQLException{
		
		// DB 연결을 시도하고 연결 객체를 반환
		// : 연결 시도 시 URL, 사용자 이름, 비밀번호를 인자로 제공
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
}
