package entities;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import dal.SubscribeManager;

@Entity
@NamedQueries({
	@NamedQuery(name = "User.username", query = "SELECT u FROM User u WHERE u.username = :username"),
	@NamedQuery(name = "User.all", query = "SELECT u FROM User")
})
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1625901988929685861L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String pwHash;
	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	private String role;
	private ArrayList<Notification> notifications;

	@SuppressWarnings("unused")
	private ArrayList<Subscribe> subscribes;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwHash() {
		return pwHash;
	}

	public void setPwHash(String pwHash) {
		this.pwHash = pwHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public ArrayList<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(ArrayList<Notification> notifications) {
		this.notifications = notifications;
	}

	public ArrayList<Subscribe> getSubscribes() {

		SubscribeManager sm = new SubscribeManager();
		ArrayList<Subscribe> subs = new ArrayList<Subscribe>(sm.getAllSubscribeByUserId(id));
		return subs;
	}

	public void setSubscribes(ArrayList<Subscribe> subscribes) {
		this.subscribes = subscribes;
	}

}
