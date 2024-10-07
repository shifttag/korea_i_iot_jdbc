package korea_1_iot_jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	// 모든 사용자 정보를 조회하는 메서드
	public List<User> findAll() throws SQLException {
		// DB 연결을 가져옴
		Connection connection = DBConnection.getConnection();
		
		// 모든 사용자 정보를 조회하는 SQL 쿼리문 작성
		String sql = "select * from user";
		
		// PreparedStatement 객체를 생성
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// 쿼리를 실행하고 결과를 ResultSet으로 전달받음
		ResultSet resultSet = statement.executeQuery();
		
		// User 객체를 저장할 리스트 생성
		List<User> users = new ArrayList<User>();
		
		// 결과 집합에서 다음 행이 있는 동안 반복
		while(resultSet.next()) {
			User user = new User(
						resultSet.getInt("id"),
						resultSet.getString("name"),
						resultSet.getString("email")
					);
			
			// 리스트에 User 객체를 추가
			users.add(user);
			
		}
		
		// 리소스 해제
		resultSet.close();
		statement.close();
		connection.close();
		
		return users;	// 조회된 사용자 User 객체를 담은 리스트 users를 반환
	}

	// 사용자 정보를 추가하는 메서드
	public void addUser(User user) throws SQLException {
		Connection connection = DBConnection.getConnection();
		
		String sql = "insert into user (name, email) values(? , ?)";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, user.getName());
		statement.setString(2, user.getEmail());
		
		statement.executeUpdate();
		
		statement.close();
		connection.close();
	}
	
	// 사용자 정보를 수정하는 메서드
	public void updateUser(User user) throws SQLException {
		Connection connection = DBConnection.getConnection();
		
		// 업데이트 할 이름과 이메일이 있는지 확인
		boolean updateName = user.getName() != null && !user.getName().isEmpty();
		boolean updateEmail = user.getEmail() != null && !user.getEmail().isEmpty();
		
		// SQL 쿼리 작성
		// cf) StringBuilder
		//		: 자바에서 가변 문자열을 만드는 클래스
		StringBuilder sql = new StringBuilder("update user set ");	// set 뒤에 띄어쓰기 해야되는거 주의!
		
		if(updateName) {
			sql.append("name = ?, ");
		}
		
		if(updateEmail) {
			sql.append("email = ?, ");
		}
		
		// 이름이나 이메일이 둘 중 하나라도 있는 경우에만 마지막 콤마를 제거
		if (updateName || updateEmail) {
			sql.deleteCharAt(sql.length() - 2);			
		}
		// >> 존재하지 않더라도 오류 X
		//		OutOfBounds Exception은 자동 처리
		
		// where절 추가
		sql.append("where id = ?");
		
		PreparedStatement statement = connection.prepareStatement(sql.toString());
		
		int parameterIndex = 1;
		if(updateName) {
			statement.setString(parameterIndex++, user.getName());
		}
		if(updateEmail) {
			statement.setString(parameterIndex++, user.getEmail());
		}
		
		statement.setInt(parameterIndex, user.getId());
		
		statement.executeUpdate();
		
		statement.close();
		connection.close();

	}
	
	public void deleteUser(int id) throws SQLException {
		try (Connection connection = DBConnection.getConnection()) {
			String sql = "delete from user where id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			
			statement.close();
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	
}
