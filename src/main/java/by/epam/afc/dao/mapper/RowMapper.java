package by.epam.afc.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T>{
    T mapRows(ResultSet rs) throws SQLException;
}
