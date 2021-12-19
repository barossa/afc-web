package by.epam.afc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The interface Row mapper.
 *
 * @param <T> the type parameter
 */
public interface RowMapper<T> {
    /**
     * Map rows t.
     *
     * @param rs the result set
     * @return the t
     * @throws SQLException the sql exception
     */
    T mapRows(ResultSet rs) throws SQLException;
}
