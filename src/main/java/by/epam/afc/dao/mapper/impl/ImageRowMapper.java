package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.ColumnName.*;

/**
 * The type Image row mapper.
 */
public class ImageRowMapper implements RowMapper<Image> {
    private static final ImageRowMapper instance = new ImageRowMapper();

    private ImageRowMapper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ImageRowMapper getInstance() {
        return instance;
    }


    /**
     * Map image rows.
     *
     * @param rs the rs
     * @return the image
     * @throws SQLException the sql exception
     */
    @Override
    public Image mapRows(ResultSet rs) throws SQLException {
        User owner = User.getBuilder()
                .id(rs.getInt(UPLOADED_BY))
                .build();

        Image image = Image.getBuilder()
                .id(rs.getInt(IMAGE_ID))
                .uploadData(rs.getTimestamp(UPLOAD_DATA).toLocalDateTime())
                .uploadedByUser(owner)
                .base64(rs.getString(BASE_64))
                .build();
        return image;
    }
}
