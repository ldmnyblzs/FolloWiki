package api;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "api")
public class Api {
	protected Query query;
	
	public Api() {
	}
	public Api(Query query) {
		super();
		this.query = query;
	}

	@XmlElement(name = "query")
	public Query getQuery() {
		return query;
	}
	public void setQuery(Query query) {
		this.query = query;
	}
}
