package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.util.List;

public class UserDao {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper<User> userRowMapper = (rs, rowNum) -> {
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));
		return user;
	};

	public void add(final User user) {
		this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES(?,?,?)", user.getId(), user.getName(), user.getPassword());
	}

	public User get(String id) {
		return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?",
				new Object[]{id}, this.userRowMapper);
	}

	public void deleteAll() {
		this.jdbcTemplate.update("DELETE FROM users");
	}

	public int getCount() {
		return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
	}

	public List<User> getAll() {
		return this.jdbcTemplate.query("SELECT * FROM users ORDER BY id", this.userRowMapper);
	}
}
