package logic;

import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.annotation.PostConstruct;

import api.Page;
import api.Revision;
import api.WikiManager;
import dal.ArticleManager;
import dal.SubscribeManager;
import entities.Article;
import entities.Notification;

@Startup
@Singleton
public class WikiModule {
	String lastCheck;

	@PostConstruct
	public void init() {
		ZonedDateTime date = ZonedDateTime.now();
		lastCheck = date.format(DateTimeFormatter.ISO_INSTANT);
	}

	@Schedule(hour = "*", minute = "0/15", second = "0", persistent = false)
	public void checkArticles() {
		ZonedDateTime date = ZonedDateTime.now();
		String thisCheck = date.format(DateTimeFormatter.ISO_INSTANT);

		WikiManager wm = new WikiManager();
		ArticleManager am = new ArticleManager();
		SubscribeManager sm = new SubscribeManager();
		Page page;

		for (Article a : am.getAllArticles()) {
			try {
				page = wm.getRevisions(a.getUrl(), thisCheck, lastCheck);
			} catch (URISyntaxException use) {
				continue;
			}
			if (page.getRevisions() != null) {
				for (Revision revision : page.getRevisions()) {
					Notification notification = new Notification();
					notification.setArticle(a);
					notification.setTitle(page.getTitle());
					notification.setComment(revision.getComment());
					notification.setDate(revision.getTimestamp());
					notification.setDeletions(revision.getDeletions());
					notification.setInsertions(revision.getInsertions());
					sm.saveNotification(notification);
				}
			}
		}
		lastCheck = thisCheck;
	}
}
