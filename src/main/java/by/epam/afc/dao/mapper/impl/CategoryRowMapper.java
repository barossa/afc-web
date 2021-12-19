package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.ColumnName.CATEGORY_DESCRIPTION;
import static by.epam.afc.dao.ColumnName.CATEGORY_ID;

/**
 * The type Category row mapper.
 */
public class CategoryRowMapper implements RowMapper<Category> {
    private static final CategoryRowMapper instance = new CategoryRowMapper();

    private CategoryRowMapper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CategoryRowMapper getInstance() {
        return instance;
    }

    /**
     * Map rows category.
     *
     * @param rs the rs
     * @return the category
     * @throws SQLException the sql exception
     */
    @Override
    public Category mapRows(ResultSet rs) throws SQLException {
        return new Category(rs.getInt(CATEGORY_ID), rs.getString(CATEGORY_DESCRIPTION));
    }
}
