package logic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import dal.SubscribeManager;
import dal.UserManager;
import entities.Notification;
import entities.Subscribe;
import entities.User;

@Singleton
public class Notifier {

	@Schedule(minute = "*/20")
	public void userNotify() {
		
		UserManager um = new UserManager();
		ArrayList<User> users = (ArrayList<User>) um.getAllUser();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String formattedDate = format1.format(cal.getTime());
		
		for (User user : users) {
			if(user.getNotifications().size() > 0){
				
				String message = "<h1>Kedves " + user.getUsername() + "!<h2>\n";
				message = "Változásokat találtunk egy vagy több Wikipéda-cikkben, amikre feliratkoztál. Kattints a linkekre a cikkek megtekintéséhez!<br/>\n";
				message = "<br/>\n";
				
				ArrayList<String> collectedUrl = new ArrayList<>();
				
				for (Notification n1 : user.getNotifications()) {

					if(!collectedUrl.contains(n1.getUrl())) {
						collectedUrl.add(n1.getUrl());
						if(isItTime(user.getId(),n1.getUrl())) break;
						
						message = "<h2>" + n1.getTitle() + "</h2><br/>\n";
						message = "<a href="+n1.getUrl()+">" + n1.getUrl() + "</a><br/>\n";
						message = "<br/>\n";
						
						for (Notification n2 : user.getNotifications()) {
							if(n1.getUrl().equals(n2.getUrl())){

								message = "<table>\n";
								message = "<tr><td>Comment:</td><td>"+n2.getComment()+"</td></tr>\n";
								message = "<tr><td>Dátum:</td><td>"+n2.getDate()+"</td></tr>\n";
								message = "<tr><td>Törölt bekezdések:</td><td>"+n2.getDeletions()+"</td></tr>\n";
								message = "<tr><td>Beszúrt bekezdések:</td><td>"+n2.getInsertions()+"</td></tr>\n";
								message = "</table>\n";
							}
							
						}
					}
				}			
				
				sendEmail(user.getEmail(), "FolloWiki értesítés - " + formattedDate, message);
			}
		}
	}

	private boolean isItTime(long userId, String url) {
		SubscribeManager sm = new SubscribeManager();
		Subscribe sub = sm.getSubscribeByUserIdAndUrl(userId, url);
		
		int frequency = sub.getFrequency();
		Calendar d = Calendar.getInstance();
		if(frequency == 15) return true;
		else if(frequency == 60 && d.get(Calendar.MINUTE) < 10 && d.get(Calendar.MINUTE) > 0) return true;
		else if(frequency == 1440 && d.get(Calendar.HOUR_OF_DAY) == 0 && d.get(Calendar.MINUTE) < 10 && d.get(Calendar.MINUTE) > 0) return true;
		else if(frequency == 10080 && d.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY && d.get(Calendar.HOUR_OF_DAY) == 0 && d.get(Calendar.MINUTE) < 10 && d.get(Calendar.MINUTE) > 0) return true;
		else if(frequency == 44640 && d.get(Calendar.DAY_OF_MONTH) == 1 && d.get(Calendar.HOUR_OF_DAY) == 0 && d.get(Calendar.MINUTE) < 10 && d.get(Calendar.MINUTE) > 0) return true;
		else return false;
	}

	public void sendEmail(String to, String subject, String msg) {
		sendEmail(to, subject, msg, "localhost", Constant.SERVER_EMAIL);
	}

	// COPY-PASTE
	public void sendEmail(String to, String subject, String msg, String host, String from) {

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Now set the actual message
			message.setContent(msg, "text/html");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
