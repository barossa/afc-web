package by.epam.afc.dao.mapper.impl;

import by.epam.afc.dao.mapper.RowMapper;
import by.epam.afc.dao.model.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import static by.epam.afc.dao.constants.ColumnName.*;

public class ImageRowMapper implements RowMapper<Image> {
    static final Logger logger = LogManager.getLogger(ImageRowMapper.class);

    @Override
    public Image mapRows(ResultSet rs) throws SQLException {
        return Image.getBuilder()
                .id(rs.getInt(IMAGE_ID))
                .uploadData(rs.getTimestamp(UPLOAD_DATA).toLocalDateTime())
                .uploadedByUser(rs.getInt(UPLOADED_BY))
                .image(readImage(rs.getBinaryStream(BIN_IMAGE), rs.getInt(IMAGE_ID)))
                .build();
    }

    private BufferedImage readImage(InputStream input, int imageId) {
        try {
            return ImageIO.read(input);
        } catch (IOException e) {
            logger.error("Can't load Image id=" + imageId, e);
        }
        return null;
    }
}
