package by.epam.afc.service.util;

import by.epam.afc.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoTransactionHelper {
    private static final DaoTransactionHelper instance = new DaoTransactionHelper();

    private DaoTransactionHelper() {
    }

    public static DaoTransactionHelper getInstance() {
        return instance;
    }

    public void rollbackConnection(Connection connection) throws DaoException {
        if(connection != null){
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DaoException("Can't rollback changes", e);
            }
        }
    }

    public void setConnectionAutocommit(Connection connection, boolean value) throws DaoException{
        if(connection != null){
            try {
                connection.setAutoCommit(value);
            } catch (SQLException e) {
                throw new DaoException("Can't change connection auto-commit value", e);
            }
        }
    }

    public void closeConnection(Connection connection) throws DaoException{
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DaoException("Error occurred while \"closing\" connection", e);
            }
        }
    }
}
