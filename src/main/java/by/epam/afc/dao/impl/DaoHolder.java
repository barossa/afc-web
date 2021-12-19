package by.epam.afc.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The type Dao holder.
 */
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

    private DaoHolder() {
    }

    /**
     * Gets user dao.
     *
     * @return the user dao
     */
    public static UserDaoImpl getUserDao() {
        return userDao;
    }

    /**
     * Gets image dao.
     *
     * @return the image dao
     */
    public static ImageDaoImpl getImageDao() {
        return imageDao;
    }

    /**
     * Gets announcement dao.
     *
     * @return the announcement dao
     */
    public static AnnouncementDaoImpl getAnnouncementDao() {
        return announcementDao;
    }

}
