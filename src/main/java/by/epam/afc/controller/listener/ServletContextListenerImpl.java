package by.epam.afc.controller.listener;

import by.epam.afc.dao.entity.Category;
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

import static by.epam.afc.controller.ContextAttribute.*;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ServletContextListenerImpl.class);

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
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //Destroying connection pool
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (DaoException e) {
            logger.error("Error occurred while destroying servlet context:", e);
        }
    }

    private List<Category> loadCategories() {
        AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
        try {
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
        AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
        try {
            List<Region> regions = announcementDao.findAllRegions();
            String message = String.format("Successfully loaded %d regions!", regions.size());
            logger.info(message);
            return regions;
        } catch (DaoException e) {
            logger.error("Error occurred while loading regions", e);
        }
        return new ArrayList<>();
    }
}
