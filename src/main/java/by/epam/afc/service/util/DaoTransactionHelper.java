package by.epam.afc.service.util;

import by.epam.afc.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The type Dao transaction helper.
 */
public class DaoTransactionHelper {
    private static final DaoTransactionHelper instance = new DaoTransactionHelper();

    private DaoTransactionHelper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DaoTransactionHelper getInstance() {
        return instance;
    }

    /**
     * Rollback connection.
     *
     * @param connection the connection
     * @throws DaoException the dao exception
     */
    public void rollbackConnection(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DaoException("Can't rollback changes", e);
            }
        }
    }

    /**
     * Sets connection autocommit.
     *
     * @param connection the connection
     * @param value      the value
     * @throws DaoException the dao exception
     */
    public void setConnectionAutocommit(Connection connection, boolean value) throws DaoException {
        if (connection != null) {
            try {
                connection.setAutoCommit(value);
            } catch (SQLException e) {
                throw new DaoException("Can't change connection auto-commit value", e);
            }
        }
    }

    /**
     * Close connection.
     *
     * @param connection the connection
     * @throws DaoException the dao exception
     */
    public void closeConnection(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException("Error occurred while \"closing\" connection", e);
            }
        }
    }
}
