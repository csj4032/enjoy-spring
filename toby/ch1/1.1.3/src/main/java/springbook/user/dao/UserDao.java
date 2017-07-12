package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

public class UserDao {

	public void add(User user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.3/springbook", "spring", "book");
		PreparedStatement ps = c.prepareStatement("INSERT INTO users (id, name, password) VALUES (?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		ps.executeUpdate();
		ps.close();
		c.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://192.168.0.3/springbook?characterEncoding=UTF-8", "spring","book");
		PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao = new UserDao();
		User user = User.builder().id("whiteship").name("백기선").password("married").build();
		//dao.add(user);
		System.out.println(user.toString() + " 등록 성공");
		User user2 = dao.get(user.getId());
		System.out.println(user2.toString() + " 조회 성공");
	}
}