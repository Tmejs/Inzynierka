package com.pjkurs.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.pjkurs.usables.MailObject;

public class MailSenderUtil {

    private final String emailSender;
    private final String emailPassword;
    private final String smtp;
    private final String port;
    private final Session session;

    public MailSenderUtil(String emailSender, String emailPassword, String smtp, String port) {
        this.emailSender = emailSender;
        this.emailPassword = emailPassword;
        this.smtp = smtp;
        this.port = port;
        session = createSession();
    }

    public void sendAsHtml(MailObject mailObject)
            throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        prepareEmailMessage(message, mailObject.getReciever(), mailObject.getTitle(),
                mailObject.getMessageBody());
        Transport.send(message);
    }

    private void prepareEmailMessage(MimeMessage message, String to, String title,
            String html)
            throws MessagingException {
        message.setContent(html, "text/html; charset=utf-8");
        message.setFrom(new InternetAddress(emailSender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(title);
    }

    private Session createSession() {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.port", port);
        return Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSender, emailPassword);
            }
        });
    }
}
