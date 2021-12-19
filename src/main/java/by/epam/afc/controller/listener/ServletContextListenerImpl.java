package by.epam.afc.controller.listener;

import by.epam.afc.dao.ImageDao;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.entity.Region;
import by.epam.afc.dao.impl.AnnouncementDaoImpl;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.exception.DaoException;
import by.epam.afc.pool.ConnectionPool;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.afc.controller.ContextAttribute.*;

/**
 * The type Servlet context listener.
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ServletContextListenerImpl.class);

    private static final int GUEST_IMAGE_ID = 3;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //Initializing connection pool
        ConnectionPool.getInstance();

        //Loading categories from DAO
        ServletContext servletContext = sce.getServletContext();
        List<Category> categories = loadCategories();
        servletContext.setAttribute(CATEGORIES, categories);

        //Loading regions from DAO
        List<Region> regions = loadRegions();
        servletContext.setAttribute(REGIONS, regions);

        //Loading guest profile image
        Image guestImage = loadGuestImage();
        servletContext.setAttribute(GUEST_IMAGE, guestImage);

        //Users to be updated
        servletContext.setAttribute(USERS_TO_UPDATE, new ArrayList<Integer>());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (DaoException e) {
            logger.error("Error occurred while destroying servlet context:", e);
        }
    }

    private List<Category> loadCategories() {
        try {
            AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
            List<Category> allCategories = announcementDao.findAllCategories();
            String message = String.format("Successfully loaded %d announcement categories!", allCategories.size());
            logger.info(message);
            return allCategories;
        } catch (DaoException e) {
            logger.error("Error occurred while loading announcement categories", e);
        }
        return new ArrayList<>();
    }

    private List<Region> loadRegions() {
        try {
            AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
            List<Region> regions = announcementDao.findAllRegions();
            String message = String.format("Successfully loaded %d regions!", regions.size());
            logger.info(message);
            return regions;
        } catch (DaoException e) {
            logger.error("Error occurred while loading regions", e);
        }
        return new ArrayList<>();
    }

    private Image loadGuestImage() {
        Image image = Image.getBuilder()
                .build();
        try {
            ImageDao imageDao = DaoHolder.getImageDao();
            Optional<Image> optionalImage = imageDao.findById(GUEST_IMAGE_ID);
            image = optionalImage.orElseThrow(DaoException::new);
        } catch (DaoException e) {
            logger.error("Error occurred while loading GUEST profile picture");
        }
        return image;
    }
}
