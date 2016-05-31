package com.navigator.util;

import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtil {
	
    public static  Properties definePropertyParameters() {
        //Properties object to set environment property parameters
    	Properties props = new Properties();
                
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.socketFactory.port", 465);
//        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.port", 465);
      //  properties.put("mail.smtp.starttls.enable", "true");

        props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
        
        return props;
    }

	public static void  sendTextMessage(String to,String link,String subject,String message2) throws Exception {
        Properties properties = definePropertyParameters();

        //mail session
        Session session = Session.getDefaultInstance(properties,
    			new javax.mail.Authenticator() {
    				protected PasswordAuthentication getPasswordAuthentication() {
    					return new PasswordAuthentication("ramazanfirin@gmail.com","ra5699mo");
    				}
    			});

        //mail message


        Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("from@no-spam.com"));
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		message.setSubject(subject);
		message.setText(message2 +
				"\n\n "+link);

		Transport.send(message);

		System.out.println("Done");
        
    }
	
	public static void main(String[] args) throws Exception {
		sendTextMessage("ramazan.firin@kgteknoloji.com","","","");
	}
	
}
