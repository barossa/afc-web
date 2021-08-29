package by.epam.afc.dao.impl;

import by.epam.afc.dao.ImageDao;
import by.epam.afc.dao.mapper.impl.ImageRowMapper;
import by.epam.afc.dao.model.Announcement;
import by.epam.afc.dao.model.Image;
import by.epam.afc.pool.ConnectionPool;
import by.epam.afc.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.afc.dao.constants.ColumnName.*;
import static by.epam.afc.dao.constants.TableName.ANNOUNCEMENT_IMAGES;
import static by.epam.afc.dao.constants.TableName.IMAGES;

public final class ImageDaoImpl implements ImageDao {

    private static final String SELECT_ALL_IMAGES = "SELECT " + IMAGE_ID + ", " + UPLOAD_DATA + ", " + UPLOADED_BY + ", " + BIN_IMAGE
            + " FROM " + IMAGES + ";";

    private static final String SELECT_BY_IMAGE_ID = "SELECT " + IMAGE_ID + ", " + UPLOAD_DATA + ", " + UPLOADED_BY + ", " + BIN_IMAGE
            + " FROM " + IMAGES
            + " WHERE " + IMAGE_ID + "=?;";

    private static final String UPDATE_BY_IMAGE_ID = "UPDATE " + IMAGES + " SET " + UPLOAD_DATA + "=?, " + UPLOADED_BY + "=?, " + BIN_IMAGE + "=?"
            + " WHERE " + IMAGE_ID + "=?;";

    private static final String INSERT_IMAGE = "INSERT INTO " + IMAGES + "(" + UPLOAD_DATA + ", " + UPLOADED_BY + ", " + BIN_IMAGE + ")"
            + "VALUES (?, ?, ?);";

    private static final String SELECT_BY_ANNOUNCEMENT = "SELECT " + IMAGE_ID + ", " + UPLOAD_DATA + ", " + UPLOADED_BY + ", " + BIN_IMAGE + ", " + ANNOUNCEMENT_ID
            + " FROM " + IMAGES
            + " INNER JOIN " + ANNOUNCEMENT_IMAGES + " ON " + IMAGES + "." + IMAGE_ID + "=" + ANNOUNCEMENT_IMAGES + "." + IMAGE_ID
            + " WHERE " + ANNOUNCEMENT_ID + "= ?;";

    private static final String DEFAULT_PROFILE_IMAGE_FORMAT = "png";

    static final Logger logger = LogManager.getLogger(ImageRowMapper.class);
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    ImageDaoImpl() {
    }

    @Override
    public List<Image> findAll() throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_IMAGES);
             ResultSet resultSet = statement.executeQuery()
        ) {

            List<Image> images = new ArrayList<>();
            ImageRowMapper mapper = new ImageRowMapper();
            while (resultSet.next()) {
                Image image = mapper.mapRows(resultSet);
                images.add(image);
            }
            logger.info("Successfully read " + images.size() + " images!");
            return images;
        } catch (SQLException e) {
            logger.error("Can't load all images", e);
            throw new DaoException("Can't load all images", e);
        }
    }

    @Override
    public Optional<Image> findById(int id) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_IMAGE_ID)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            ImageRowMapper mapper = new ImageRowMapper();
            if (resultSet.next()) {
                Image image = mapper.mapRows(resultSet);
                return Optional.of(image);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Can't find image by id.", e);
            throw new DaoException("Can't find image by id", e);
        }
    }

    @Override
    public Optional<Image> update(Image image) throws DaoException {
        Optional<Image> byId = findById(image.getId());
        if (!byId.isPresent()) {
            logger.error("Can't update image with id=" + image.getId() + " which is not presented!");
            return Optional.empty();
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_IMAGE_ID)) {
            statement.setTimestamp(1, Timestamp.valueOf(image.getUploadData()));
            statement.setInt(2, image.getUploadedByUserId());
            statement.setBinaryStream(3, getImageStream(image.getImage()));
            statement.setInt(4, image.getId());
            statement.execute();
            return Optional.of(image);

        } catch (SQLException e) {
            logger.error("Can't update image by id=" + image.getId(), e);
            throw new DaoException("Can't update image by id", e);
        }
    }

    @Override
    public Optional<Image> save(Image image) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_IMAGE,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setTimestamp(1, Timestamp.valueOf(image.getUploadData()));
            statement.setInt(2, image.getUploadedByUserId());
            statement.setBinaryStream(3, getImageStream(image.getImage()));
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int imageId = generatedKeys.getInt(IMAGE_ID);
                image.setId(imageId);
                return Optional.of(image);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't save new image!", e);
            throw new DaoException("Can't save new image.", e);
        }
    }

    @Override
    public List<Image> findByAnnouncement(Announcement announcement) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ANNOUNCEMENT)) {

            statement.setInt(1, announcement.getId());
            ResultSet resultSet = statement.executeQuery();
            List<Image> images = new ArrayList<>();
            ImageRowMapper mapper = new ImageRowMapper();
            while (resultSet.next()) {
                Image image = mapper.mapRows(resultSet);
                images.add(image);
            }
            return images;

        } catch (SQLException e) {
            logger.error("Can't load images by announcements!", e);
            throw new DaoException("Can't load images by announcement", e);
        }
    }

    private InputStream getImageStream(BufferedImage image) throws DaoException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, DEFAULT_PROFILE_IMAGE_FORMAT, outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            logger.error("Can't transform Image to InputStream");
            throw new DaoException("Can't transform incorrect image!", e);
        }
    }
}
