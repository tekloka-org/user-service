package org.tekloka.user.util;

import java.io.UnsupportedEncodingException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	
	private final Logger logger = LoggerFactory.getLogger(EmailUtil.class);
	
	@Value("${smtp.host}") 
	private String smtpHost;
	
	@Value("${smtp.port}") 
	private int smtpPort;
	
	@Value("${smtp.password}") 
	private String smtpPassword;
	
	@Value("${smtp.fromEmail}") 
	private String smtpFromEmail;
	
	@Value("${smtp.fromName}") 
	private String smtpFromName;
	
	public void sendEmail(String to, String subject, String body) throws MessagingException, UnsupportedEncodingException {
	 
		  var properties = System.getProperties();
		  properties.put("mail.smtp.host", smtpHost);
		  properties.put("mail.smtp.port", smtpPort);
		  properties.put("mail.smtp.ssl.enable", "true");
		  properties.put("mail.smtp.auth", "true");
	      
    	  var session = Session.getInstance(properties, new javax.mail.Authenticator() {
				@Override
    		  	protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(smtpFromEmail, smtpPassword);
				}
		  });
    	  
          var message = new MimeMessage(session);
          message.setFrom(new InternetAddress(smtpFromEmail, smtpFromName));
          message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
          message.setSubject(subject);
          message.setContent(body,"text/html");
          Transport.send(message);
          logger.info("Email sent!");
    	  
	}

}
