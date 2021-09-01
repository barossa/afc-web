package by.epam.afc.pool;

import by.epam.afc.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static Logger logger = LogManager.getLogger(ConnectionPool.class);

    private static final AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static final Lock lock = new ReentrantLock(true);
    private static final int INITIAL_POOL_SIZE = 10;

    private static ConnectionPool instance = null;

    private final LinkedBlockingQueue<ProxyConnection> freeConnections;
    private final LinkedBlockingQueue<ProxyConnection> busyConnections;

    private ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(INITIAL_POOL_SIZE);
        busyConnections = new LinkedBlockingQueue<>(INITIAL_POOL_SIZE);
        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        try {
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                freeConnections.offer((ProxyConnection) connectionFactory.getConnection());
            }
            if (freeConnections.isEmpty()) {
                logger.fatal("Unable to initialize connection pool");
                throw new RuntimeException("Unable to initialize connection pool");
            }
            logger.info("Connection pool initialized successfully.");
        } catch (DaoException e) {
            logger.fatal("Unable to initialize connection pool: ", e);
            throw new RuntimeException("Unable to initialize connection pool: ", e);
        }
    }

    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            busyConnections.put(connection);
            logger.debug("Supplied connection: [" + busyConnections.size() + "b\\" + freeConnections.size() + "f]");
        } catch (InterruptedException e) {
            logger.error("Error occurred while supplying connection:", e);
        }
        return connection;
    }

    public boolean releaseConnection(Connection connection) {
        if (connection instanceof ProxyConnection) {
            ProxyConnection proxyConnection = (ProxyConnection) connection;
            try {
                busyConnections.remove(proxyConnection);
                freeConnections.put(proxyConnection);
                logger.debug("Released connection: [" + busyConnections.size() + "b\\" + freeConnections.size() + "f]");
                return true;
            } catch (InterruptedException e) {
                logger.error("Connection could not being released!");
                return false;
            }
        } else {
            logger.error("Invalid connection could not being released: not instanceof" + ConnectionPool.class);
            return false;
        }
    }

    public void destroyPool() throws DaoException {
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            try {
                ProxyConnection connection = freeConnections.take();
                connection.closeDirectly();
            } catch (SQLException | InterruptedException e) {
                logger.error("Error occurred while destroying pool: ", e);
                throw new DaoException("Error occurred while destroying connection pool!");
            }
        }
        deregisterDriver();
        logger.info("Pool destroyed successfully!");
    }

    private void deregisterDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        boolean atLeastOneDeregister = false;
        while (true) {
            try {
                Driver driver = drivers.nextElement();
                DriverManager.deregisterDriver(driver);
                atLeastOneDeregister = true;
                logger.info("JDBC driver deregister.");
            } catch (SQLException e) {
                logger.error("Error occurred while deregister JDBC driver: ", e);
            } catch (NoSuchElementException e) {
                if (!atLeastOneDeregister) {
                    logger.error("Can't deregister no one JDBC driver!");
                }
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "ConnectionPool{" +
                "freeConnections=" + freeConnections +
                ", busyConnections=" + busyConnections +
                '}';
    }
}
