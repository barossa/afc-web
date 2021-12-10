package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.mapper.RowMapper;
import by.epam.afc.service.util.ImageHelper;
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
    private static final ImageRowMapper instance = new ImageRowMapper();

    private static final Logger logger = LogManager.getLogger(ImageRowMapper.class);

    private ImageRowMapper(){}

    public static ImageRowMapper getInstance(){
        return instance;
    }


    @Override
    public Image mapRows(ResultSet rs) throws SQLException {
        User owner = User.getBuilder()
                .id(rs.getInt(UPLOADED_BY))
                .build();

        Image image =  Image.getBuilder()
                .id(rs.getInt(IMAGE_ID))
                .uploadData(rs.getTimestamp(UPLOAD_DATA).toLocalDateTime())
                .uploadedByUser(owner)
                .base64(readImage(rs.getBinaryStream(BIN_IMAGE), rs.getInt(IMAGE_ID)))
                .build();
        return image;
    }

    private String readImage(InputStream input, int imageId) {
        try {
            ImageHelper imageHelper = ImageHelper.getInstance();
            byte[] bytes = imageHelper.readInputStream(input);

            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            logger.error("Can't load Image id=" + imageId, e);
        }
        return "";
    }
}
