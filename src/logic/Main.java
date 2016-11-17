package logic;

import dal.UserManager;
import entities.User;

public class Main {

	public static void main(String[] args) {
		
		UserManager um = new UserManager();
		um.createUser("TesztGéza", "asd", "valami@valami.hu");
		User u = um.getUserByUsername("TesztGéza");
		System.out.println("User " + u.getUsername() + " loaded from database");
	}

}
