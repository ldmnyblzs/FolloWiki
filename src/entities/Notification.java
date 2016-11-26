package entities; 

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Notification implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2112602753470142089L;

	@Id
	long id;
	int diffCharSum;
	// String originalText;
	// String newText;
	@Column(nullable=false) 
	Date date;
	String comment;
	@Column(nullable=false) 
	String url;
	@Column(nullable=false) 
	String title;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDiffCharSum() {
		return diffCharSum;
	}

	public void setDiffCharSum(int diffCharSum) {
		this.diffCharSum = diffCharSum;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
