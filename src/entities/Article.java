package entities;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Article.url", query = "SELECT a FROM Article a WHERE a.url = :url")
public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8467591699250373007L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String url;
	private ArrayList<String> differences;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<String> getDifferences() {
		return differences;
	}

	public void setDifferences(ArrayList<String> differences) {
		this.differences = differences;
	}

}
