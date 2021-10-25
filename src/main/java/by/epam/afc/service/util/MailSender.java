package by.epam.afc.service.util;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class MailSender extends Thread {
    private static final MailSender instance = new MailSender();

    private static Logger logger = LogManager.getLogger(MailSender.class);

    private static final String USERNAME = "mail.username";
    private static final String PASSWORD = "mail.password";

    private final LinkedBlockingQueue<Message> messagesToSent;

    private static Session session;

    private MailSender() {
        try {
            messagesToSent = new LinkedBlockingQueue<>();
            InputStream propsAsStream = getClass().getResourceAsStream("prop/mail.properties");
            Properties mailProps = new Properties();
            mailProps.load(propsAsStream);
            session = Session.getInstance(mailProps, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailProps.getProperty(USERNAME), mailProps.getProperty(PASSWORD));
                }
            });
            start();
        } catch (IOException e) {
            logger.error("Can't load MailSender util", e);
            throw new ExceptionInInitializerError("Can't load MailSender util: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = messagesToSent.take();
                Transport.send(message);
            } catch (InterruptedException ex) {
                logger.debug("Shutting down MailSender util");
            } catch (MessagingException e) {
                logger.error("Can't send message", e);
            }
        }
    }

    public static MailSender getInstance() {
        return instance;
    }

    public boolean sendEmail(Message message) {
        return messagesToSent.offer(message);
    }

    public MimeMessage createEmptyMessage() {
        return new MimeMessage(session);
    }

}

