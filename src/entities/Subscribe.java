package entities; 

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries( {
	@NamedQuery(name = "Subscribe.user", query = "SELECT s FROM Subscribe s WHERE s.user = :user"),
	@NamedQuery(name = "Subscribe.userAndArticle", query = "SELECT s FROM Subscribe s WHERE s.article = :article AND s.user = :user")
})
public class Subscribe implements Serializable {

	private static final long serialVersionUID = 2128726431812515775L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	@ManyToOne
	@JoinColumn(name = "ARTICLE_ID")
	private Article article;
	@Column(nullable=false) 
	private int frequency;
	@Column(nullable=false)
	private int sensitivity;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public int getSensitivity() {
		return sensitivity;
	}
	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}
	public int getFrequency() {
		if(frequency > 44640) return 44640;
		else if(frequency > 10080) return 10080;
		else if(frequency > 1440) return 1440;
		else if(frequency > 60) return 60;
		else return 15;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
}
