package by.epam.afc.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DaoHolder {
    static final Logger logger = LogManager.getLogger(DaoHolder.class);

    private static final UserDaoImpl userDao;
    private static final MessageDaoImpl messageDao;
    private static final ImageDaoImpl imageDao;
    private static final DialogDaoImpl dialogDao;
    private static final AnnouncementDaoImpl announcementDao;

    static {
        userDao = new UserDaoImpl();
        messageDao = new MessageDaoImpl();
        imageDao = new ImageDaoImpl();
        dialogDao = new DialogDaoImpl();
        announcementDao = new AnnouncementDaoImpl();
        logger.info("Dao initialized successfully.");
    }

    private DaoHolder(){
    }

    public static UserDaoImpl getUserDao(){
        return userDao;
    }

    public static MessageDaoImpl getMessageDao(){
        return messageDao;
    }

    public static ImageDaoImpl getImageDao(){
        return imageDao;
    }

    public static DialogDaoImpl getDialogDao(){
        return dialogDao;
    }

    public static AnnouncementDaoImpl getAnnouncementDao(){
        return announcementDao;
    }

}
