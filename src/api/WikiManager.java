package api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class WikiManager {

	public List<Revision> getRevisions(String url, String latestRevision) throws URISyntaxException{
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(getApiUri(url, latestRevision));
		javax.ws.rs.core.Response response = target.request(MediaType.APPLICATION_XML).get();
		Api a = response.readEntity(Api.class);
		return a.getQuery().getPages().get(0).getRevisions();
	}

	protected URI getApiUri(String uri, String latestRevision) throws URISyntaxException {
		URI theuri = new URI(uri);
		String[] title = theuri.getPath().split("/");
		return UriBuilder
				.fromUri(
						"https://{arg1}/w/api.php?action=query&prop=revisions&rvprop=%1Fids%1Fflags%1Ftimestamp%1Fparsedcomment%1Ftags&rvdiffto=prev&rvlimit=max&format=xml&titles={arg2}&rvendid={arg3}&rvlimit=max")
				.build(theuri.getHost(), title[2], latestRevision);
	}
}
