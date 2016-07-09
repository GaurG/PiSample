package com.accenture.pi;

import java.util.Base64;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public class MailNotifier {

	public static void main(String[] args) {
		final String username = "tt5587438@gmail.com";
		final String password = "bWFpbC5zbXRwLmF1dGg=";

		// Decode data on other side, by processing encoded data
		byte[] valueDecoded= Base64.getDecoder().decode(password.getBytes() );

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, new String(valueDecoded));
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("tt5587438@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("tt5587438@gmail.com"));
			Date date = new Date(System.currentTimeMillis());
			message.setSubject("Testing Subject " + date.toString());
			message.setText("Dear Mail Crawler," + "\n\n This is  new mail! ");

			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
