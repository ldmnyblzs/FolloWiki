package api;

import javax.xml.bind.annotation.XmlValue;

public class Diff {
	protected String content;
	
	public Diff() {
	}
	public Diff(String content) {
		super();
		this.content = content;
	}
	
	@XmlValue
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
