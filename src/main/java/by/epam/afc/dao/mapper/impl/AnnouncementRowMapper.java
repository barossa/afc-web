package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.mapper.RowMapper;
import by.epam.afc.dao.model.Announcement;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.constants.ColumnName.*;

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
                .publicationDate(rs.getTimestamp(PUBLICATION_DATE))
                .announcementStatus(Announcement.Status.valueOf(rs.getString(STATUS_DESCRIPTION)))
                .category(Announcement.Category.valueOf(rs.getString(CATEGORY_DESCRIPTION)))
                .build();
    }
}
