package logic;

import dal.UserManager;
import entities.User;

public class Main {

	public static void main(String[] args) {
		
		UserManager um = new UserManager();
		um.createUser("TesztG�za", "asd", "valami@valami.hu");
		User u = um.getUserByUsername("TesztG�za");
		System.out.println("User " + u.getUsername() + " loaded from database");
	}

}
