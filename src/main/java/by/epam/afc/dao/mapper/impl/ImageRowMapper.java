package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import static by.epam.afc.dao.ColumnName.*;

public class ImageRowMapper implements RowMapper<Image> {
    static final Logger logger = LogManager.getLogger(ImageRowMapper.class);

    private static final int EOF = -1;

    @Override
    public Image mapRows(ResultSet rs) throws SQLException {
        return Image.getBuilder()
                .id(rs.getInt(IMAGE_ID))
                .uploadData(rs.getTimestamp(UPLOAD_DATA).toLocalDateTime())
                .uploadedByUser(rs.getInt(UPLOADED_BY))
                .base64(readImage(rs.getBinaryStream(BIN_IMAGE), rs.getInt(IMAGE_ID)))
                .build();
    }

    private String readImage(InputStream input, int imageId) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            while (true) {
                int value = input.read();
                if (value == EOF) {
                    break;
                } else {
                    outputStream.write(value);
                }
            }
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            logger.error("Can't load Image id=" + imageId, e);
        }
        return null;
    }
}
