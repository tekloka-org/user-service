package org.tekloka.user.util;

import java.io.UnsupportedEncodingException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
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
	
	@Value("${smtp.username}") 
	private String smtpUsername;
	
	@Value("${smtp.password}") 
	private String smtpPassword;
	
	@Value("${smtp.from}") 
	private String smtpFrom;
	
	@Value("${smtp.fromName}") 
	private String smtpFromName;
	
	public void sendEmail(String to, String subject, String body) throws MessagingException, UnsupportedEncodingException {
		
		var props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", smtpPort); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");
 
    	var session = Session.getDefaultInstance(props);
 
        var msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(smtpFrom, smtpFromName));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setContent(body,"text/html");
        
        var transport = session.getTransport();
        logger.info("Sending email");   
        transport.connect(smtpHost, smtpUsername, smtpPassword);
        transport.sendMessage(msg, msg.getAllRecipients());
        logger.info("Email sent!");
        transport.close();
        
	}

}
