package springbook.user.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springbook.user.domain.User;

import java.sql.SQLException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class UserDaoTest {

	@Autowired
	private ApplicationContext context;
	private UserDao dao;

	@Before
	public void setUp() {
		dao = context.getBean("userDao", UserDao.class);
	}

	@Test
	public void addAndGet() throws SQLException {

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		User user = new User();
		user.setId("genius1");
		user.setName("최성조");
		user.setPassword("1234");

		dao.add(user);

		//assertThat(dao.getCount(), is(1));

		User user2 = dao.get(user.getId());

		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
	}

	@Test
	public void count() throws SQLException {

		User user1 = new User("genius1", "최성조", "1234");
		User user2 = new User("genius2", "최성조", "1234");
		User user3 = new User("genius3", "최성조", "1234");
		User user4 = new User("genius4", "최성조", "1234");

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));

		dao.add(user1);
		assertThat(dao.getCount(), is(1));

		dao.add(user2);
		assertThat(dao.getCount(), is(2));

		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		dao.get("genius1");
	}
}