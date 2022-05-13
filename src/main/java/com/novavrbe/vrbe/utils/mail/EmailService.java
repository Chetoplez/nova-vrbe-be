package com.novavrbe.vrbe.utils.mail;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;


@Service
public class EmailService {
        private final Properties prop;

        public EmailService() {
            prop = new Properties();
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.username", "gestione@fervm.it");
            prop.put("mail.smtp.password", "@Claudio89");
            prop.put("mail.smtp.host", "ssl0.ovh.net");
            prop.put("mail.smtp.port", 587);
            prop.put("mail.smtp.ssl.trust", "ssl0.ovh.net");
        }

    public void sendMail(MailDetails email) throws Exception {

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("gestione@fervm.it", "@Claudio89");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("gestione@fervm.it"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getRecipient()));
        message.setSubject(email.getSubject());


        MimeBodyPart mimeBodyPartWithStyledText = new MimeBodyPart();
        mimeBodyPartWithStyledText.setContent(email.getBody(), "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPartWithStyledText);

        message.setContent(multipart);
        Transport.send(message);
    }

}
