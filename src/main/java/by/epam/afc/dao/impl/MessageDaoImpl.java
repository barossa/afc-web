package by.epam.afc.dao.impl;

import by.epam.afc.dao.MessageDao;
import by.epam.afc.dao.entity.Dialog;
import by.epam.afc.dao.entity.Message;
import by.epam.afc.dao.mapper.impl.MessageRowMapper;
import by.epam.afc.exception.DaoException;
import by.epam.afc.pool.ConnectionPool;
import by.epam.afc.service.util.SearchHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.afc.dao.ColumnName.*;
import static by.epam.afc.dao.TableName.MESSAGES;
import static by.epam.afc.dao.entity.BaseEntity.UNDEFINED_ID;
import static by.epam.afc.service.validator.impl.SearchRequestValidatorImpl.SPLIT_CHARACTER;

public final class MessageDaoImpl implements MessageDao {

    private static final String SELECT_ALL_MESSAGES = "SELECT " + MESSAGE_ID + "," + DIALOG_ID + "," + SENDER_ID + "," + SENT_TIME + ","
            + TEXT_CONTENT + "," + GRAPHIC_CONTENT + "," + MESSAGES + "." + IMAGE_ID
            + " FROM " + MESSAGES + ";";

    private static final String SELECT_BY_MESSAGE_ID = "SELECT " + MESSAGE_ID + "," + DIALOG_ID + "," + SENDER_ID + "," + SENT_TIME + ","
            + TEXT_CONTENT + "," + GRAPHIC_CONTENT + "," + IMAGE_ID
            + " FROM " + MESSAGES
            + " WHERE " + MESSAGE_ID + "=?;";

    private static final String SELECT_BY_DIALOG_ID = "SELECT " + MESSAGE_ID + "," + DIALOG_ID + "," + SENDER_ID + "," + SENT_TIME + ","
            + TEXT_CONTENT + "," + GRAPHIC_CONTENT + "," + IMAGE_ID
            + " FROM " + MESSAGES
            + " WHERE " + DIALOG_ID + "=?;";

    private static final String SELECT_BY_REGEX = "SELECT " + MESSAGE_ID + "," + DIALOG_ID + "," + SENDER_ID + "," + SENT_TIME + ","
            + TEXT_CONTENT + "," + GRAPHIC_CONTENT + "," + IMAGE_ID
            + " FROM " + MESSAGES
            + " WHERE " + TEXT_CONTENT;

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
                ResultSet resultSet = statement.executeQuery()
        ) {
            List<Message> messages = new ArrayList<>();
            MessageRowMapper messageMapper = MessageRowMapper.getInstance();
            while (resultSet.next()) {
                Message message = messageMapper.mapRows(resultSet);
                messages.add(message);
            }
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
                MessageRowMapper messageMapper = MessageRowMapper.getInstance();
                Message message = messageMapper.mapRows(resultSet);
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

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BY_MESSAGE_ID)) {

            insertMessage(message, statement);
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

            insertMessage(message, statement);
            ResultSet generatedKeys = statement.getGeneratedKeys();

            if (generatedKeys.next()) {
                int messageId = generatedKeys.getInt(ID_KEY);
                return findById(messageId);
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
        String[] patterns = part.split(SPLIT_CHARACTER);
        SearchHelper helper = SearchHelper.getInstance();

        String query = helper.completeRegexpQuery(SELECT_BY_REGEX, patterns);
        List<Message> messages = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            MessageRowMapper mapper = MessageRowMapper.getInstance();
            while (resultSet.next()) {
                Message message = mapper.mapRows(resultSet);
                messages.add(message);
            }
            return messages;

        } catch (SQLException e) {
            logger.error("Can't find messages by part.", e);
            throw new DaoException("Can't find messages by part.", e);
        }
    }

    @Override
    public List<Message> findByDialog(Dialog dialog) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_DIALOG_ID)) {
            statement.setInt(1, dialog.getId());
            ResultSet resultSet = statement.executeQuery();

            List<Message> messages = new ArrayList<>();
            MessageRowMapper messageMapper = MessageRowMapper.getInstance();
            while (resultSet.next()) {
                Message message = messageMapper.mapRows(resultSet);
                messages.add(message);
            }
            return messages;
        } catch (SQLException e) {
            logger.error("Can't find message by dialog:", e);
            throw new DaoException("Can't find message by dialog:", e);
        }
    }

    private void insertMessage(Message message, PreparedStatement statement) throws SQLException {
        statement.setInt(1, message.getDialogId());
        statement.setInt(2, message.getSenderId());
        statement.setTimestamp(3, Timestamp.valueOf(message.getSentTime()));
        statement.setString(4, message.getText());
        statement.setBoolean(5, message.isGraphicsContent());

        if (message.isGraphicsContent()) {
            statement.setInt(6, message.getImage().getId());
        } else {
            statement.setInt(6, UNDEFINED_ID);
        }
        statement.execute();
    }
}
