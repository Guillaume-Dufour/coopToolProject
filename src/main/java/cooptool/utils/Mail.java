package cooptool.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Mail class
 */
public class Mail {

    private static final String ADRESSE = PropertiesResource.getMailProperties().getProperty("ADDR_MAIL");
    private static final String PASSWORD = PropertiesResource.getMailProperties().getProperty("PASSWORD_MAIL");
    private static final Session SESSION = creerSession();

    private static Session creerSession() {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        return Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(ADRESSE, PASSWORD);
                    }
                });
    }

    /**
     * Method called to send an email with the provided information
     * @param subject Subject of the mail
     * @param text Content of the mail
     * @param adrReceiver Recipients of the mail
     */
    public static void sendMail(String subject, String text, String adrReceiver) {

        try {

            Message message = new MimeMessage(SESSION);
            message.setFrom(new InternetAddress(ADRESSE));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(adrReceiver));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

