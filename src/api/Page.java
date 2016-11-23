package api;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Page {
	private int pageId;
	private int ns;
	private String title;
	private List<Revision> revisions;
	
	public Page() {
	}
	public Page(int pageid, int ns, String title, List<Revision> revisions) {
		super();
		this.pageId = pageid;
		this.ns = ns;
		this.title = title;
		this.revisions = revisions;
	}

	@XmlAttribute(name = "pageid")
	public int getPageId() {
		return pageId;
	}
	public void setPageId(int pageId) {
		this.pageId = pageId;
	}
	
	@XmlAttribute(name = "ns")
	public int getNs() {
		return ns;
	}
	public void setNs(int ns) {
		this.ns = ns;
	}
	
	@XmlAttribute(name = "title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@XmlElementWrapper(name = "revisions")
	@XmlElement(name = "rev", type = Revision.class)
	public List<Revision> getRevisions() {
		return revisions;
	}
	public void setRevisions(List<Revision> revisions) {
		this.revisions = revisions;
	}
}
