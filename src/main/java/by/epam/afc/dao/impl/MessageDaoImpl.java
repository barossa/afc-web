package by.epam.afc.dao.impl;

import by.epam.afc.dao.MessageDao;
import by.epam.afc.dao.mapper.impl.ImageRowMapper;
import by.epam.afc.dao.mapper.impl.MessageRowMapper;
import by.epam.afc.dao.model.Dialog;
import by.epam.afc.dao.model.Image;
import by.epam.afc.dao.model.Message;
import by.epam.afc.pool.ConnectionPool;
import by.epam.afc.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.afc.dao.constants.ColumnName.*;
import static by.epam.afc.dao.constants.TableName.IMAGES;
import static by.epam.afc.dao.constants.TableName.MESSAGES;

public final class MessageDaoImpl implements MessageDao {

    private static final String SELECT_ALL_MESSAGES = "SELECT " + MESSAGE_ID + "," + DIALOG_ID + "," + SENDER_ID + "," + SENT_TIME + ","
            + TEXT_CONTENT + "," + GRAPHIC_CONTENT + "," + MESSAGES + "." + IMAGE_ID + "," + UPLOAD_DATA + "," + UPLOADED_BY + "," + BIN_IMAGE
            + " FROM " + MESSAGES
            + " INNER JOIN " + IMAGES + " ON " + MESSAGES + "." + IMAGE_ID + "=" + IMAGES + "." + IMAGE_ID + ";";

    private static final String SELECT_BY_MESSAGE_ID = "SELECT " + MESSAGE_ID + "," + DIALOG_ID + "," + SENDER_ID + "," + SENT_TIME + ","
            + TEXT_CONTENT + "," + GRAPHIC_CONTENT + "," + IMAGE_ID + "," + UPLOAD_DATA + "," + UPLOADED_BY + "," + BIN_IMAGE
            + " FROM " + MESSAGES
            + " INNER JOIN " + IMAGES + " ON " + MESSAGES + "." + IMAGE_ID + "=" + IMAGES + "." + IMAGE_ID
            + " WHERE " + MESSAGE_ID + "=?;";

    private static final String SELECT_BY_DIALOG_ID = "SELECT " + MESSAGE_ID + "," + DIALOG_ID + "," + SENDER_ID + "," + SENT_TIME + ","
            + TEXT_CONTENT + "," + GRAPHIC_CONTENT + "," + IMAGE_ID + "," + UPLOAD_DATA + "," + UPLOADED_BY + "," + BIN_IMAGE
            + " FROM " + MESSAGES
            + " INNER JOIN " + IMAGES + " ON " + MESSAGES + "." + IMAGE_ID + "=" + IMAGES + "." + IMAGE_ID
            + " WHERE " + DIALOG_ID + "=?;";

    private static final String UPDATE_BY_MESSAGE_ID = "UPDATE " + MESSAGES + " SET " + DIALOG_ID + "=?," + SENDER_ID + "=?," + SENT_TIME + "=?,"
            + TEXT_CONTENT + "=?," + GRAPHIC_CONTENT + "=?," + IMAGE_ID + "=? " + "WHERE " + MESSAGE_ID + " =?;";

    private static final String INSERT_MESSAGE = "INSERT INTO " + MESSAGES + "(" + DIALOG_ID + "," + SENDER_ID + "," + SENT_TIME + ","
            + TEXT_CONTENT + "," + GRAPHIC_CONTENT + "," + IMAGE_ID + ") VALUES(?,?,?,?,?,?);";

    static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();


    @Override
    public List<Message> findAll() throws DaoException {
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_MESSAGES);
                ResultSet resultSet = statement.executeQuery();
        ) {
            List<Message> messages = new ArrayList<>();
            MessageRowMapper messageMapper = new MessageRowMapper();
            while (resultSet.next()) {
                Message message = messageMapper.mapRows(resultSet);
                if (message.isGraphicsContent()) {
                    ImageRowMapper imageMapper = new ImageRowMapper();
                    Image image = imageMapper.mapRows(resultSet);
                    message.setImage(image);
                }
                messages.add(message);
            }
            logger.info("Successfully read " + messages.size() + " messages!");
            return messages;
        } catch (SQLException e) {
            logger.error("Can't load all messages!", e);
            throw new DaoException("Can't load all messages!", e);
        }
    }

    @Override
    public Optional<Message> findById(int id) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_MESSAGE_ID)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                MessageRowMapper messageMapper = new MessageRowMapper();
                Message message = messageMapper.mapRows(resultSet);
                if (message.isGraphicsContent()) {
                    ImageRowMapper imageMapper = new ImageRowMapper();
                    Image image = imageMapper.mapRows(resultSet);
                    message.setImage(image);
                }
                logger.debug("Found message by id=" + id);
                return Optional.of(message);
            } else {
                logger.debug("Cant find message by id=" + id);
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.error("Finding message by id failed", e);
            throw new DaoException("Can't find message by id", e);
        }
    }

    @Override
    public Optional<Message> update(Message message) throws DaoException {
        Optional<Message> byId = findById(message.getId());
        if (!byId.isPresent()) {
            logger.error("Can't update message with id=" + message.getId() + " which is not presented!");
            return Optional.empty();
        }
        Message oldMessage = byId.get();

        if (message.isGraphicsContent()) {
            if (!oldMessage.getImage().equals(message.getImage())) {
                ImageDaoImpl imageDao = DaoHolder.getImageDao();
                Optional<Image> savedImage = imageDao.save(message.getImage());
                if (savedImage.isPresent()) {
                    message.setImage(savedImage.get());
                } else {
                    logger.error("Can't update new message pinned image with id=" + message.getId());
                    throw new DaoException("Can't update new message image.");
                }

            }
        }

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_MESSAGE_ID)) {

            statement.setInt(1, message.getDialogId());
            statement.setInt(2, message.getSenderId());
            statement.setTimestamp(3, Timestamp.valueOf(message.getSentTime()));
            statement.setString(4, message.getText());
            statement.setBoolean(5, message.isGraphicsContent());

            if (message.isGraphicsContent()) {
                statement.setInt(6, message.getImage().getId());
            } else {
                statement.setInt(6, Image.UNDEFINED_IMAGE_ID);
            }

            statement.execute();
            return Optional.of(message);
        } catch (SQLException e) {
            logger.error("Can't update message by id=" + message.getId(), e);
            throw new DaoException("Can't update message by id!", e);
        }
    }

    @Override
    public Optional<Message> save(Message message) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_MESSAGE,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, message.getDialogId());
            statement.setInt(2, message.getSenderId());
            statement.setTimestamp(3, Timestamp.valueOf(message.getSentTime()));
            statement.setString(4, message.getText());
            statement.setBoolean(5, message.isGraphicsContent());

            if (message.isGraphicsContent()) {
                ImageDaoImpl imageDao = DaoHolder.getImageDao();
                Optional<Image> savedImage = imageDao.save(message.getImage());
                if (savedImage.isPresent()) {
                    message.setImage(savedImage.get());
                } else {
                    logger.error("Can't save pinned image from message with id=" + message.getId());
                    throw new DaoException("Can't save new image from message!");
                }
            } else {
                statement.setInt(6, Image.UNDEFINED_IMAGE_ID);
            }

            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int messageId = generatedKeys.getInt(MESSAGE_ID);
                message.setId(messageId);
                return Optional.of(message);
            } else {
                logger.error("Can't get generated keys from Result Set!");
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't save new message.", e);
            throw new DaoException("Can't save new message.", e);
        }
    }

    @Override
    public List<Message> findByPart(String part) throws DaoException {
        //// TODO: 8/20/21 PROTECT FROM SQL INJECTION
        return null;
    }

    @Override
    public List<Message> findByDialog(Dialog dialog) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_DIALOG_ID)) {
            statement.setInt(1, dialog.getId());
            ResultSet resultSet = statement.executeQuery();

            List<Message> messages = new ArrayList<>();
            MessageRowMapper messageMapper = new MessageRowMapper();
            ImageRowMapper imageMapper = new ImageRowMapper();
            while (resultSet.next()) {
                Message message = messageMapper.mapRows(resultSet);
                if (message.isGraphicsContent()) {
                    Image image = imageMapper.mapRows(resultSet);
                    message.setImage(image);
                }
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            logger.error("Can't find message by dialog:", e);
            throw new DaoException("Can't find message by dialog:", e);
        }
    }
}
