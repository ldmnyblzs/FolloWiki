package entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries( {
	@NamedQuery(name = "Subscribe.userId", query = "SELECT s FROM Subscribe s WHERE s.user.id = :userid"),
	@NamedQuery(name = "User.articleUrl", query = "SELECT s.user FROM Subscribe s WHERE s.article.url = :url"),
	@NamedQuery(name = "ArticleUrl.toSubsbribe", query = "SELECT s.article.url FROM Subscribe s")
})
public class Subscribe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2128726431812515775L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique=true, nullable=false) 
	private User user;
	@Column(unique=true, nullable=false) 
	private Article article;
	@Column(nullable=false) 
	private int frequency;
	@Column(nullable=false) 
	private int sensitivity;

	public int getSensitivity() {
		return sensitivity;
	}

	public void setSensitivity(int sensitivity) {
		this.sensitivity = sensitivity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}
