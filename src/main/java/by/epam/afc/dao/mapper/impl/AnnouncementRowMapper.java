package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.mapper.RowMapper;
import by.epam.afc.dao.entity.Announcement;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.ColumnName.*;

public class AnnouncementRowMapper implements RowMapper<Announcement> {
    @Override
    public Announcement mapRows(ResultSet rs) throws SQLException {
        return Announcement.getBuilder()
                .id(rs.getInt(ANNOUNCEMENT_ID))
                .ownerId(rs.getInt(OWNER_ID))
                .title(rs.getString(TITLE))
                .price(rs.getBigDecimal(PRICE))
                .primaryImageId(rs.getInt(PROFILE_IMAGE_ID))
                .description(rs.getString(DESCRIPTION))
                .publicationDate(rs.getTimestamp(PUBLICATION_DATE).toLocalDateTime())
                .announcementStatus(Announcement.Status.valueOf(rs.getString(STATUS_DESCRIPTION)))
                .category(new Category(rs.getInt(CATEGORY_ID), rs.getString(CATEGORY_DESCRIPTION)))
                .build();
    }
}
