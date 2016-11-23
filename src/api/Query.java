package api;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Query {
	protected List<Page> pages = new ArrayList<>();

	public Query()
	{
	}
	public Query(List<Page> pages) {
		super();
		this.pages = pages;
	}

	@XmlElementWrapper(name = "pages")
	@XmlElement(name = "page", type = Page.class)
	public List<Page> getPages() {
		return pages;
	}
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
}
