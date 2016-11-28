package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import dal.SubscribeManager;

@Entity
@NamedQueries({ @NamedQuery(name = "User.username", query = "SELECT u FROM User u WHERE u.username = :username"),
		@NamedQuery(name = "User.article", query = "SELECT u FROM User u, Subscribe s WHERE s.user = u AND s.article = :article"),
		@NamedQuery(name = "User.all", query = "SELECT u FROM User u") })
public class User implements Serializable {

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
	@OneToMany(mappedBy = "user")
	private List<Notification> notifications;
	@OneToMany(mappedBy = "user")
	private List<Subscribe> subscribes;

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

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Subscribe> getSubscribes() {
		return subscribes;
	}

	public void setSubscribes(List<Subscribe> subscribes) {
		this.subscribes = subscribes;
	}
}
