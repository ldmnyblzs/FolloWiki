package api;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Revision {
	protected String id;
	protected Revision parent;
	protected String minor;
	protected Date timestamp;
	protected String comment;
	protected List<String> tags;
	protected Diff diff;

	public Revision() {
	}
	public Revision(String id, Revision parent, String minor, Date timestamp, String comment, List<String> tags,
			Diff diff) {
		super();
		this.id = id;
		this.parent = parent;
		this.minor = minor;
		this.timestamp = timestamp;
		this.comment = comment;
		this.tags = tags;
		this.diff = diff;
	}

	@XmlID
	@XmlAttribute(name = "revid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@XmlIDREF
	@XmlAttribute(name = "parentid")
	public Revision getParent() {
		return parent;
	}
	public void setParent(Revision parent) {
		this.parent = parent;
	}

	@XmlAttribute(name = "minor")
	public String getMinor() {
		return minor;
	}
	public void setMinor(String minor) {
		this.minor = minor;
	}
	public boolean isMinor() {
		return minor.equals("");
	}

	@XmlAttribute(name = "timestamp")
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@XmlAttribute(name = "parsedcomment")
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	@XmlElement(name = "tags")
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@XmlElement(name = "diff")
	public Diff getDiff() {
		return diff;
	}
	public void setDiff(Diff diff) {
		this.diff = diff;
	}
}
