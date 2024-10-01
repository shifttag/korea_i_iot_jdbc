package korea_1_iot_jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import korea_1_iot_jdbc.db.DBConnection;
import korea_1_iot_jdbc.entity.User;

// DAO (Data Access Object)
// : DB와 같은 영구 저장소에 접근하는 객체
// : 데이터 처리 로직과 DB 작업을 분리하는 역할

// DAO 패턴
// : DB와의 CRUD 작업을 처리


// UserDAO 클래스 정의
public class UserDAO {
	// 아이디를 기준으로 사용자 정보를 가져오는 메서드
	public User getUserById(int id) throws SQLException {
		// DBConnection을 통해 DB 연결을 가져옴
		Connection connection = DBConnection.getConnection();
		
		// 실행할 SQL문 작성: id를 조건으로 사용자 정보 조회
		// : 동적 파라미터
		String sql = "select * from user where id = ?";
		
		// SQL 쿼리를 실행하기 위해 PreparedStatement 객체를 생성
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// 실행 객체에 파라미터값을 설정
		statement.setInt(1, id);

		// SQL 쿼리를 실행하고 결과를 ResultSet으로 저장
		ResultSet resultSet = statement.executeQuery();
		
		User user = null;	// User 객체를 null로 초기화
		if (resultSet.next()) {	// 결과 집합에 다음 행이 있으면 true를 반환
			user = new User(resultSet.getInt("id"), 
					resultSet.getString("name"), 
					resultSet.getString("email"));
		}
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return user;	// 조회된 사용자 정보를 담은 User 객체 반환
		
	
	}
}
