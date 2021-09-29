package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.entity.Region;
import by.epam.afc.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.ColumnName.REGION_ID;
import static by.epam.afc.dao.ColumnName.REGION_NAME;

public class RegionRowMapper implements RowMapper<Region> {
    private static final RegionRowMapper instance = new RegionRowMapper();

    private RegionRowMapper(){}

    public static RegionRowMapper getInstance(){
        return instance;
    }

    @Override
    public Region mapRows(ResultSet rs) throws SQLException {
        int id = rs.getInt(REGION_ID);
        String name = rs.getString(REGION_NAME);
        return new Region(id, name);
    }
}
