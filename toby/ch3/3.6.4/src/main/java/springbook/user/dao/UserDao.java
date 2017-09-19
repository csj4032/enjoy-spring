package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void add(User user) throws SQLException {
		jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
	}

	public User get(String id) throws SQLException {
		return jdbcTemplate.query(
				(Connection con) -> con.prepareStatement("SELECT id, name, password FROM users WHERE id = ?"),
				(PreparedStatement ps) -> ps.setString(1, id),
				(ResultSet rs) -> {
					User user = new User();
					if (rs.next()) {
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
					}
					return user;
				}
		);
	}

	public User getByName(String name) throws SQLException {
		return jdbcTemplate.queryForObject(
				"SELECT id, name, password FROM users WHERE name = ?",
				new Object[]{name},
				(ResultSet rs, int rowNum) -> {
					User user = new User();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));

					return user;
				}
		);
	}

	public void deleteAll() throws SQLException {
		jdbcTemplate.update("delete from users;");
	}

	public int getCount() throws SQLException {
		return jdbcTemplate.query(
				(Connection con) -> con.prepareStatement("SELECT COUNT(*) FROM users"),
				(ResultSet rs) -> {
					rs.next();
					return rs.getInt(1);
				});
	}

	private PreparedStatement makeStatement(Connection connection) throws SQLException {
		PreparedStatement ps;
		ps = connection.prepareStatement("DELETE FROM users;");
		return ps;
	}


}
