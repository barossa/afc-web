package by.epam.afc.pool;

import by.epam.afc.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The type Connection factory.
 */
class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);

    private static final String PROPERTIES_PATH = "/prop/db.properties";
    private static final String DRIVER_NAME_KEY = "driver";
    private static final String URL_PROPERTY_NAME = "url";
    private static final Properties props = new Properties();

    private static ConnectionFactory instance;

    static {
        String driverName = null;
        try (InputStream inputStream = ConnectionFactory.class.getResourceAsStream(PROPERTIES_PATH)) {
            props.load(inputStream);
            driverName = (String) props.get(DRIVER_NAME_KEY);
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            logger.fatal("Can't register driver: " + driverName, e);
            throw new ExceptionInInitializerError("Can't register driver: " + driverName);
        } catch (IOException e) {
            logger.fatal("Can't load db config: ", e);
            throw new ExceptionInInitializerError("Can't load properties file");
        }
    }

    /**
     * Instantiates a new Connection factory.
     */
    ConnectionFactory() {
    }

    /**
     * Get instance connection factory.
     *
     * @return the connection factory
     */
    static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws DaoException the dao exception
     */
    Connection getConnection() throws DaoException {
        try {
            Connection connection = DriverManager.getConnection(props.getProperty(URL_PROPERTY_NAME), props);
            return new ProxyConnection(connection);
        } catch (SQLException e) {
            logger.fatal("Can't reach database!", e);
            throw new DaoException("Connection to database failed!", e);
        }
    }
}
