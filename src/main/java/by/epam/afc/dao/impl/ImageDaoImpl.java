package by.epam.afc.dao.impl;

import by.epam.afc.dao.ImageDao;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.mapper.impl.ImageRowMapper;
import by.epam.afc.exception.DaoException;
import by.epam.afc.pool.ConnectionPool;
import by.epam.afc.service.util.DaoTransactionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.afc.dao.ColumnName.*;
import static by.epam.afc.dao.TableName.ANNOUNCEMENT_IMAGES;
import static by.epam.afc.dao.TableName.IMAGES;

public final class ImageDaoImpl implements ImageDao {

    private static final String SELECT_ALL_IMAGES = "SELECT " + IMAGE_ID + ", " + UPLOAD_DATA + ", " + UPLOADED_BY + ", " + BASE_64
            + " FROM " + IMAGES + ";";

    private static final String SELECT_BY_IMAGE_ID = "SELECT " + IMAGE_ID + ", " + UPLOAD_DATA + ", " + UPLOADED_BY + ", " + BASE_64
            + " FROM " + IMAGES
            + " WHERE " + IMAGE_ID + "=?;";

    private static final String UPDATE_BY_IMAGE_ID = "UPDATE " + IMAGES + " SET " + UPLOAD_DATA + "=?, " + UPLOADED_BY + "=?, " + BASE_64 + "=?"
            + " WHERE " + IMAGE_ID + "=?;";

    private static final String INSERT_IMAGE = "INSERT INTO " + IMAGES + "(" + UPLOAD_DATA + ", " + UPLOADED_BY + ", " + BASE_64 + ")"
            + "VALUES (?, ?, ?);";

    private static final String INSERT_ANNOUNCEMENT_IMAGE_DATA = "INSERT INTO " + ANNOUNCEMENT_IMAGES + "(" + ANNOUNCEMENT_ID + "," + IMAGE_ID + ")"
            + "VALUES (?,?);";

    private static final String SELECT_BY_ANNOUNCEMENT_ID = "SELECT " + ANNOUNCEMENT_IMAGES + "." + IMAGE_ID + ", " + UPLOAD_DATA + ", " + UPLOADED_BY + ", " + BASE_64 + ", " + ANNOUNCEMENT_ID
            + " FROM " + IMAGES
            + " INNER JOIN " + ANNOUNCEMENT_IMAGES + " ON " + IMAGES + "." + IMAGE_ID + "=" + ANNOUNCEMENT_IMAGES + "." + IMAGE_ID
            + " WHERE " + ANNOUNCEMENT_ID + "=?;";

    private static final Logger logger = LogManager.getLogger(ImageRowMapper.class);
    private final ConnectionPool pool = ConnectionPool.getInstance();

    ImageDaoImpl() {
    }

    @Override
    public List<Image> findAll() throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_IMAGES);
             ResultSet resultSet = statement.executeQuery()
        ) {

            List<Image> images = new ArrayList<>();
            ImageRowMapper mapper = ImageRowMapper.getInstance();
            while (resultSet.next()) {
                Image image = mapper.mapRows(resultSet);
                images.add(image);
            }
            return images;

        } catch (SQLException e) {
            logger.error("Can't load all images", e);
            throw new DaoException("Can't load all images", e);
        }
    }

    @Override
    public Optional<Image> findById(int id) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_IMAGE_ID)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            ImageRowMapper mapper = ImageRowMapper.getInstance();
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
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_IMAGE_ID)) {
            statement.setTimestamp(1, Timestamp.valueOf(image.getUploadData()));
            statement.setInt(2, image.getUploadedBy().getId());
            statement.setString(3, image.getBase64());
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
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_IMAGE,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            fillInsertStatement(statement, image);
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int imageId = generatedKeys.getInt(ID_KEY);
                return findById(imageId);
            } else {
                logger.error("Can't get generated keys from Result Set!");
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't save new image!", e);
            throw new DaoException("Can't save new image.", e);
        }
    }

    @Override
    public List<Image> findByAnnouncement(Announcement announcement) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ANNOUNCEMENT_ID)) {

            statement.setInt(1, announcement.getId());
            ResultSet resultSet = statement.executeQuery();

            List<Image> images = new ArrayList<>();
            ImageRowMapper mapper = ImageRowMapper.getInstance();
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

    @Override
    public List<Image> saveAnnouncementImages(Announcement announcement) throws DaoException {
        List<Image> imagesToSave = announcement.getImages();
        if (imagesToSave.isEmpty()) {
            return imagesToSave;
        }

        DaoTransactionHelper transactionHelper = DaoTransactionHelper.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement insertImage = connection.prepareStatement(INSERT_IMAGE,
                PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement insertData = connection.prepareStatement(INSERT_ANNOUNCEMENT_IMAGE_DATA)) {
            connection.setAutoCommit(false);

            List<Image> savedImages = new ArrayList<>();
            for (Image image : imagesToSave) {
                image.setUploadedBy(announcement.getOwner());
                fillInsertStatement(insertImage, image);
                insertImage.execute();

                ResultSet generatedKeys = insertImage.getGeneratedKeys();
                applyGeneratedKey(generatedKeys, image);

                insertData.setInt(1, announcement.getId());
                insertData.setInt(2, image.getId());
                insertData.execute();
                savedImages.add(image);
            }

            connection.commit();
            return savedImages;

        } catch (SQLException e) {
            transactionHelper.rollbackConnection(connection);
            logger.error("Can't save announcement images", e);
            throw new DaoException("Can't save announcement images", e);
        } finally {
            transactionHelper.setConnectionAutocommit(connection, true);
            transactionHelper.closeConnection(connection);
        }

    }

    private void fillInsertStatement(PreparedStatement statement, Image image) throws SQLException {
        statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
        statement.setInt(2, image.getUploadedBy().getId());
        statement.setString(3, image.getBase64());
    }

    private void applyGeneratedKey(ResultSet generatedKeys, Image image) throws SQLException {
        if (generatedKeys.next()) {
            int generatedId = generatedKeys.getInt(ID_KEY);
            image.setId(generatedId);
        } else {
            logger.error("Result set doesn't contain generated image key!");
        }

    }
}
