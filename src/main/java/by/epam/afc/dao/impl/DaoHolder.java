package by.epam.afc.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DaoHolder {
    private static final Logger logger = LogManager.getLogger(DaoHolder.class);

    private static final UserDaoImpl userDao;
    private static final ImageDaoImpl imageDao;
    private static final AnnouncementDaoImpl announcementDao;

    static {
        userDao = new UserDaoImpl();
        imageDao = new ImageDaoImpl();
        announcementDao = new AnnouncementDaoImpl();
        logger.info("Dao initialized successfully.");
    }

    private DaoHolder(){
    }

    public static UserDaoImpl getUserDao(){
        return userDao;
    }

    public static ImageDaoImpl getImageDao(){
        return imageDao;
    }

    public static AnnouncementDaoImpl getAnnouncementDao(){
        return announcementDao;
    }

}
