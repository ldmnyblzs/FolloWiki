package logic;

// File Name SendEmail.java
import java.util.Properties;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Singleton
public class Notifier {

	@Schedule(minute = "*/15")
	public void userNotify() {
		// UserManager um = new UserManager();
		// ArrayList<User> ua = um.getAllUsers();
		// User u = new User();
		// u.getNotifications().get(0)
	}

	public void sendEmail(String to, String subject, String msg) {
		sendEmail(to, subject, msg, "localhost", "d.bogancs@gmail.com");
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
			message.setText(msg);

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
