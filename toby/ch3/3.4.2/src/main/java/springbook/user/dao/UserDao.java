package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.jdbcContext = new JdbcContext();
		jdbcContext.setDataSource(dataSource);
		this.dataSource = dataSource;
	}

	private JdbcContext jdbcContext;

	public void add(User user) throws SQLException {
		jdbcContext.executeSql(user, "insert into users(id, name, password) values(?,?,?)");
	}

	public User get(String id) throws SQLException {
		Connection c = this.dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("select id, name, password from users where id = ?");

		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();

		User user = null;

		if (rs.next()) {
			user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}

		rs.close();
		ps.close();
		c.close();

		if (user == null) throw new EmptyResultDataAccessException(1);

		return user;
	}

	public void deleteAll() throws SQLException {
		jdbcContext.executeSql("delete from users;");
	}

	public int getCount() throws SQLException {
		Connection c = dataSource.getConnection();
		PreparedStatement ps = c.prepareStatement("select count(*) from users");

		ResultSet rs = ps.executeQuery();
		rs.next();
		int count = rs.getInt(1);

		rs.close();
		ps.close();
		c.close();

		return count;
	}

	private PreparedStatement makeStatement(Connection connection) throws SQLException {
		PreparedStatement ps;
		ps = connection.prepareStatement("delete from users;");
		return ps;
	}


}
