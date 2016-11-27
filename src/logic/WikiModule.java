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
	public void checkArticles() throws URISyntaxException {
		LocalDate date = LocalDate.now();
		String thisCheck = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

		SubscribeManager sm = new SubscribeManager();
		List<String> urls = sm.getAllArticleUrl();
		WikiManager wm = new WikiManager();

		for (String url : urls) {
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
		}
		lastCheck = thisCheck;
	}
}
