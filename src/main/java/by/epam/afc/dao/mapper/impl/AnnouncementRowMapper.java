package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.entity.*;
import by.epam.afc.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.ColumnName.*;

public class AnnouncementRowMapper implements RowMapper<Announcement> {
    private static final AnnouncementRowMapper instance = new AnnouncementRowMapper();

    private AnnouncementRowMapper(){}

    public static AnnouncementRowMapper getInstance(){
        return instance;
    }

    @Override
    public Announcement mapRows(ResultSet rs) throws SQLException {
        User owner = User.getBuilder()
                .id(rs.getInt(USER_ID))
                .build();

        Category category = new Category(rs.getInt(CATEGORY_ID), rs.getString(CATEGORY_DESCRIPTION));

        Region region = new Region(rs.getInt(REGION_ID));

        Announcement result =  Announcement.getBuilder()
                .id(rs.getInt(ANNOUNCEMENT_ID))
                .owner(owner)
                .title(rs.getString(TITLE))
                .price(rs.getBigDecimal(PRICE))
                .primaryImageNumber(rs.getInt(PRIMARY_IMAGE_NUMBER))
                .description(rs.getString(DESCRIPTION))
                .publicationDate(rs.getTimestamp(PUBLICATION_DATE).toLocalDateTime())
                .announcementStatus(Announcement.Status.valueOf(rs.getString(STATUS_DESCRIPTION)))
                .category(category)
                .region(region)
                .build();
        return result;
    }
}
