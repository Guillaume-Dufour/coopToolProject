package cooptool.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

    public static final String ADRESSE = PropertiesResource.getMailProperties().getProperty("ADDR_MAIL");
    public static final String PASSWORD = PropertiesResource.getMailProperties().getProperty("PASSWORD_MAIL");
    public static final Session SESSION = creerSession();

    private static Session creerSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(ADRESSE, PASSWORD);
                    }
                });
    }

    public static void sendMail(String subject, String text, String adrReceiver) {
        try {
            Message message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(ADRESSE));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(adrReceiver));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            System.out.println("Message_envoye");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

