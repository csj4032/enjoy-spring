package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoJdbc implements UserDao {

	private JdbcTemplate jdbcTemplate;
	private RowMapper<User> userRowMapper = (rs, rowNum) -> User.builder().id(rs.getString("id")).name(rs.getString("name")).password(rs.getString("password")).build();

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void add(User user) {
		this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES(?,?,?)", user.getId(), user.getName(), user.getPassword());
	}

	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?",
				new Object[] {id}, this.userRowMapper);
	}

	public void deleteAll() {
		this.jdbcTemplate.update("delete from users");
	}

	public Integer getCount() {
		return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
	}

	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id",this.userRowMapper);
	}
}
