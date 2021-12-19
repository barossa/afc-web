package by.epam.afc.service.util;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Mail sender.
 */
public class MailSender {
    private static final Logger logger = LogManager.getLogger();
    private static final String PROPERTY_PATH = "prop/mail.properties";
    private static final String USERNAME_KEY = "mail.username";
    private static final String PASSWORD_KEY = "mail.password";
    private static final String CONTENT_TYPE = "text/html";

    private static final Properties properties = new Properties();

    static {
        try {
            InputStream inputStream = MailSender.class.getClassLoader().getResourceAsStream(PROPERTY_PATH);
            properties.load(inputStream);
        } catch (IOException e) {
            logger.fatal("Unable to load MailSender properties:", e);
        }
    }

    /**
     * Send mail.
     *
     * @param userMail    the user mail
     * @param messageText the message text
     * @param subject     the subject
     */
    public void send(String userMail, String messageText, String subject) {
        try {
            MimeMessage message = initMessage(userMail, messageText, subject);
            Transport.send(message);
        } catch (AddressException e) {
            logger.warn("Invalid address: {} {}", userMail, e);
        } catch (MessagingException e) {
            logger.warn("Error generating or sending message: ", e);
        }
    }


    private MimeMessage initMessage(String userMail, String messageText, String subject) throws MessagingException {
        Session mailSession = createSession(properties);
        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(subject);
        message.setContent(messageText, CONTENT_TYPE);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(userMail));
        return message;
    }


    private Session createSession(Properties configProperties) {
        String username = configProperties.getProperty(USERNAME_KEY);
        String password = configProperties.getProperty(PASSWORD_KEY);
        return Session.getDefaultInstance(configProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }
}



