package entities;

import java.io.Serializable;
import java.util.Date;

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
	String originalText;
	String newText;
	Date oldaDate;
	Date newDate;

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

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

	public String getNewText() {
		return newText;
	}

	public void setNewText(String newText) {
		this.newText = newText;
	}

	public Date getOldaDate() {
		return oldaDate;
	}

	public void setOldaDate(Date oldaDate) {
		this.oldaDate = oldaDate;
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}
}
