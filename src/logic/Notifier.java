package logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Iterator;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import dal.SubscribeManager;
import dal.UserManager;
import entities.Article;
import entities.Notification;
import entities.Subscribe;
import entities.User;

@Singleton
public class Notifier {

	@Schedule(hour = "*", minute = "2/5", second = "0", persistent = false)
	public void userNotify() {

		UserManager um = new UserManager();
		List<User> users = um.getAllUser();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String formattedDate = format1.format(cal.getTime());

		for (User user : users) {
			List<Notification> notifications = user.getNotifications();
			if (notifications.size() > 0) {

				String message = "<h1>Kedves " + user.getUsername() + "!<h2>\n";
				message += "Változásokat találtunk egy vagy több Wikipéda-cikkben, amikre feliratkoztál.<br/>\n";
				message += "<br/>\n";

				boolean shouldSend = false;

				Iterator<Notification> it = notifications.iterator();
				
				while(it.hasNext()) {
					Notification n = it.next();
					if (isItTime(user, n.getArticle())) {
						message += "<table>\n";
						message += "<tr><td>URL</td><td>" + n.getArticle().getUrl() + "</td></tr>\n";
						message += "<tr><td>Megjegyzés</td><td>" + n.getComment() + "</td></tr>\n";
						message += "<tr><td>Dátum</td><td>" + n.getDate() + "</td></tr>\n";
						message += "<tr><td>Törölt bekezdések:</td><td>" + n.getDeletions() + "</td></tr>\n";
						message += "<tr><td>Beszúrt bekezdések:</td><td>" + n.getInsertions() + "</td></tr>\n";
						message += "</table>\n";
						shouldSend = true;
						it.remove();
					}
				}
				user.setNotifications(notifications);
				um.updateUser(user.getId(), user.getUsername(), user.getPwHash(), user.getEmail(), user.getNotifications());;

				if (shouldSend)
					sendEmail(user.getEmail(), "FolloWiki értesítés - " + formattedDate, message);
			}
		}
	}

	private boolean isItTime(User user, Article article) {
		SubscribeManager sm = new SubscribeManager();
		Subscribe sub = sm.getSubscribeByUserAndArticle(user, article);

		int frequency = sub.getFrequency();
		Calendar d = Calendar.getInstance();
		if (frequency == 15)
			return true;
		else if (frequency == 60 && d.get(Calendar.MINUTE) < 10 && d.get(Calendar.MINUTE) > 0)
			return true;
		else if (frequency == 1440 && d.get(Calendar.HOUR_OF_DAY) == 0 && d.get(Calendar.MINUTE) < 10
				&& d.get(Calendar.MINUTE) > 0)
			return true;
		else if (frequency == 10080 && d.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				&& d.get(Calendar.HOUR_OF_DAY) == 0 && d.get(Calendar.MINUTE) < 10 && d.get(Calendar.MINUTE) > 0)
			return true;
		else if (frequency == 44640 && d.get(Calendar.DAY_OF_MONTH) == 1 && d.get(Calendar.HOUR_OF_DAY) == 0
				&& d.get(Calendar.MINUTE) < 10 && d.get(Calendar.MINUTE) > 0)
			return true;
		else
			return false;
	}

	public void sendEmail(String to, String subject, String msg) {
		try {
			InitialContext ctx = new InitialContext();
			Session session = (Session) ctx.lookup("mail/followiki");
			
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("followiki"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent(msg, "text/html");
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}
}
