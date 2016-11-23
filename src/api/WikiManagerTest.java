package api;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Test;

import api.WikiManager;

public class WikiManagerTest {

	@Test
	public void testUri() throws URISyntaxException {
		WikiManager wm = new WikiManager();
		URI uri = wm.getApiUri("https://en.wikipedia.org/wiki/Hungary", "750552416");
		assertEquals(uri.toString(), "https://en.wikipedia.org/w/api.php?action=query&prop=revisions&rvprop=%1Fids%1Fflags%1Ftimestamp%1Fparsedcomment%1Ftags&rvdiffto=prev&rvlimit=max&format=xml&titles=Hungary&rvendid=750552416&rvlimit=max");
	}
	
	@Test
	public void testRequest() throws URISyntaxException {
		WikiManager wm = new WikiManager();
		List<Revision> revisions = wm.getRevisions("https://en.wikipedia.org/wiki/Hungary", "750552416");
		revisions.forEach(r -> System.out.println(r.comment));
	}
}
