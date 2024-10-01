package korea_1_iot_jdbc.entity;

// Entity
// : 데이터베이스 테이블과 매핑되는 클래스

public class User {
	private int id;
	private String name;
	private String email;
	
	public User(int id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public int getId() { return id; }
	public String getName() { return name; }
	public String getEmail() { return email; }
	
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
}
