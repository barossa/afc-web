package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.ColumnName.*;

public class CategoryRowMapper implements RowMapper<Category> {
    @Override
    public Category mapRows(ResultSet rs) throws SQLException {
        return new Category(rs.getInt(CATEGORY_ID), rs.getString(CATEGORY_DESCRIPTION));
    }
}
