package api;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class WikiManager {

	public Page getRevisions(String url, String from, String to) throws URISyntaxException{
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(getApiUri(url, from, to));
		javax.ws.rs.core.Response response = target.request(MediaType.APPLICATION_XML).get();
		Api a = response.readEntity(Api.class);
		return a.getQuery().getPages().get(0);
	}

	protected URI getApiUri(String uri, String from, String to) throws URISyntaxException {
		URI theuri = new URI(uri);
		String[] title = theuri.getPath().split("/");
		return UriBuilder
				.fromUri(
						"{arg1}://{arg2}:{arg3}/w/api.php?action=query&prop=revisions&rvprop=ids%7Cflags%7Ctimestamp%7Cparsedcomment%7Ctags&rvdiffto=prev&rvlimit=max&format=xml&titles={arg4}&rvstart={arg5}&rvend={arg6}&rvlimit=max")
				.build(theuri.getScheme(), theuri.getHost(), theuri.getPort(), title[2], from, to);
	}
}
