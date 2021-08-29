package by.epam.afc.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DaoHolder {
    static final Logger logger = LogManager.getLogger(DaoHolder.class);

    private static final UserDaoImpl userDao = new UserDaoImpl();
    private static final MessageDaoImpl messageDao = new MessageDaoImpl();
    private static final ImageDaoImpl imageDao = new ImageDaoImpl();
    private static final DialogDaoImpl dialogDao = new DialogDaoImpl();
    private static final AnnouncementDaoImpl announcementDao = new AnnouncementDaoImpl();

    static {
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
