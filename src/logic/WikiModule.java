package logic;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

import api.Page;
import api.Revision;
import api.WikiManager;
import dal.SubscribeManager;
import entities.Notification;

@Singleton
public class WikiModule {
	String lastCheck;

	public WikiModule() {
		LocalDate date = LocalDate.now();
		lastCheck = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
	}

	@Schedule(minute = "*/15")
	public void checkArticles() {
		LocalDate date = LocalDate.now();
		String thisCheck = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

		SubscribeManager sm = new SubscribeManager();
		List<String> urls = sm.getAllArticleUrl();

		for (String url : urls) {
			WikiManager wm = new WikiManager();
			try {
				Page page = wm.getRevisions(url, lastCheck, thisCheck);
				for (Revision revision : page.getRevisions()) {
					Notification notification = new Notification();
					notification.setUrl(url);
					notification.setTitle(page.getTitle());
					notification.setComment(revision.getComment());
					notification.setDate(revision.getTimestamp());
					notification.setDeletions(revision.getDeletions());
					notification.setInsertions(revision.getInsertions());
					sm.saveNotification(notification);
				}
			} catch (URISyntaxException e) {
				// Silently ignore page if not available
			}
		}
		lastCheck = thisCheck;
	}
}
