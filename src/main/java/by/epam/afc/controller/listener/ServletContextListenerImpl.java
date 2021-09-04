package by.epam.afc.controller.listener;

import by.epam.afc.exception.DaoException;
import by.epam.afc.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ServletContextListenerImpl.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //Initializing connection pool
        ConnectionPool.getInstance();
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
}
