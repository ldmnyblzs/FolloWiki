package entities;

import java.util.ArrayList;

import javax.persistence.Entity;

@Entity
public class User {
	
	String username;
	String pwHash;
	String email;
	ArrayList<String> notifications;
	
}
