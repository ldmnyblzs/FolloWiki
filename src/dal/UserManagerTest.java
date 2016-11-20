package dal;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import entities.User;
import logic.Constant;

public class UserManagerTest {

	UserManager um;
	String username;

	@Before
	public void createTestEntities() {
		
		um = new UserManager();
		Random r = new Random();
		String code = new Integer(r.nextInt(10000)).toString();
		username = "TesztG�za_" + code;
	}

	@Test
	public void testLoginUser() {

		fail("Not yet implemented");
	}

	@Test
	public void testCreateUser() {
		
		um.createUser(username, "tesztpw", "teszt@teszt.hu", Constant.USER_ROLE);
		User u = um.getUserByUsername(username);
		
		assertEquals(username, u.getUsername());

		um.deleteUser(u.getId());
	}

	@Test(expected = NullPointerException.class)
	public void testDeleteUser() {

		um.createUser(username, "tesztpw", "teszt@teszt.hu", Constant.USER_ROLE);
		User u = um.getUserByUsername(username);
		long id = u.getId();
		um.deleteUser(id);

		User u2 = um.getUserById(id);
		System.out.println(u2.getUsername());
	}

	@Test
	public void testGetUserByUsername() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetUserById() {
		fail("Not yet implemented");
	}

}
