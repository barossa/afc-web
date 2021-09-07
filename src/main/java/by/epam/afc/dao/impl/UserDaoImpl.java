package by.epam.afc.dao.impl;

import by.epam.afc.dao.UserDao;
import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.mapper.impl.ImageRowMapper;
import by.epam.afc.dao.mapper.impl.UserRowMapper;
import by.epam.afc.exception.DaoException;
import by.epam.afc.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.afc.dao.ColumnName.*;
import static by.epam.afc.dao.TableName.*;

public final class UserDaoImpl implements UserDao {

    private static final String SELECT_ALL_USERS = "SELECT " + USER_ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", "
            + LOGIN + ", " + EMAIL + ", " + PHONE + ", " + ROLE_DESCRIPTION + ", " + STATUS_DESCRIPTION + ", "
            + ABOUT + ", " + IMAGE_ID + ", " + UPLOAD_DATA + ", " + UPLOADED_BY + ", " + BIN_IMAGE
            + " FROM " + USERS
            + " INNER JOIN " + IMAGES + " ON " + USERS + "." + PROFILE_IMAGE_ID + "=" + IMAGES + "." + IMAGE_ID
            + " INNER JOIN " + USER_STATUSES + " ON " + USERS + "." + STATUS_ID + "=" + USER_STATUSES + "." + STATUS_ID
            + " INNER JOIN " + USER_ROLES + " ON " + USERS + "." + ROLE_ID + "=" + USER_ROLES + "." + ROLE_ID + ";";

    private static final String SELECT_BY_ID = "SELECT " + USER_ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", "
            + LOGIN + ", " + EMAIL + ", " + PHONE + ", " + ROLE_DESCRIPTION + ", " + STATUS_DESCRIPTION + ", "
            + ABOUT + ", " + IMAGE_ID + ", " + UPLOAD_DATA + ", " + UPLOADED_BY + ", " + BIN_IMAGE
            + " FROM " + USERS
            + " INNER JOIN " + IMAGES + " ON " + USERS + "." + PROFILE_IMAGE_ID + "=" + IMAGES + "." + IMAGE_ID
            + " INNER JOIN " + USER_STATUSES + " ON " + USERS + "." + STATUS_ID + "=" + USER_STATUSES + "." + STATUS_ID
            + " INNER JOIN " + USER_ROLES + " ON " + USERS + "." + ROLE_ID + "=" + USER_ROLES + "." + ROLE_ID
            + " WHERE " + USER_ID + " = " + "?" + ";";

    private static final String SELECT_USER_ID_BY_LOGIN = "SELECT " + USER_ID + " FROM " + USERS
            + " WHERE " + LOGIN + "=?;";

    private static final String SELECT_USER_ID_BY_EMAIL = "SELECT " + USER_ID + " FROM " + USERS
            + " WHERE " + EMAIL + "=?;";

    private static final String SELECT_USER_ID_BY_PHONE = "SELECT " + USER_ID + " FROM " + USERS
            + " WHERE " + PHONE + "=?;";

    private static final String UPDATE_USER = "UPDATE " + USERS + " SET " + FIRST_NAME + "=?, " + LAST_NAME + "=?, " + LOGIN + "=?, "
            + EMAIL + "=?, " + PHONE + "=?, " + ROLE_ID + "=?, " + STATUS_ID + "=?, " + ABOUT + "=?, " + PROFILE_IMAGE_ID + "=?"
            + " WHERE " + USER_ID + "=?;";

    private static final String INSERT_NEW_USER = "INSERT INTO " + USERS + " (" + FIRST_NAME + ", " + LAST_NAME + ", " + LOGIN + ", "
            + PASSWORD + ", " + EMAIL + ", " + PHONE + ", " + ROLE_ID + ", " + STATUS_ID + ", " + ABOUT + ", " + PROFILE_IMAGE_ID + ") "
            + "VALUES(?,?,?,?,?,?,?,?,?,?);";

    private static final String SELECT_PASSWORD_BY_USER_ID = "SELECT " + PASSWORD + " FROM " + USERS
            + " WHERE " + USER_ID + "=?;";

    private static final String UPDATE_USER_PASSWORD = "UPDATE " + USERS + " SET " + PASSWORD + "=? WHERE " + USER_ID + "=?;";

    private static final String UNDEFINED_USER_PASSWORD = "UNDEFINED";
    private static final int UNDEFINED_USER_IMAGE_ID = Image.UNDEFINED_IMAGE_ID;
    private static final int DEFAULT_USER_IMAGE_ID = 3;

    static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private final ConnectionPool pool = ConnectionPool.getInstance();

    UserDaoImpl() {
    }

    @Override
    public List<User> findAll() throws DaoException {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
                ResultSet resultSet = statement.executeQuery()
        ) {

            List<User> users = new ArrayList<>();
            UserRowMapper userMapper = new UserRowMapper();
            ImageRowMapper imageMapper = new ImageRowMapper();
            while (resultSet.next()) {
                User user = userMapper.mapRows(resultSet);
                Image profileImage = imageMapper.mapRows(resultSet);
                user.setProfileImage(profileImage);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error("Error occurred while loading all users", e);
            throw new DaoException("Error occurred while loading all users", e);
        }
    }

    @Override
    public Optional<User> findById(int id) throws DaoException {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            UserRowMapper userMapper = new UserRowMapper();
            ImageRowMapper imageMapper = new ImageRowMapper();

            User user = userMapper.mapRows(resultSet);
            Image profileImage = imageMapper.mapRows(resultSet);
            user.setProfileImage(profileImage);
            return Optional.of(user);
        } catch (SQLException e) {
            logger.error("Can't find user by ID", e);
            throw new DaoException("Can't find user by ID", e);
        }
    }

    @Override
    public Optional<User> update(User user) throws DaoException {
        Optional<User> toUpdate = findById(user.getId());
        if (!toUpdate.isPresent()) {
            logger.warn("User id=" + user.getId() + " is not presented for update!");
            return Optional.empty();
        }

        if (user.getProfileImage().getId() == UNDEFINED_USER_IMAGE_ID) {
            ImageDaoImpl imageDao = DaoHolder.getImageDao();
            Optional<Image> imageOptional = imageDao.save(user.getProfileImage());
            if (imageOptional.isPresent()) {
                user.setProfileImage(imageOptional.get());
            } else {
                logger.error("Can't update user id=" + user.getId() + " profile image!");
                throw new DaoException("Can't update user profile image!");
            }
        }

        try (
                Connection connection = pool.getConnection();
                PreparedStatement updateUser = connection.prepareStatement(UPDATE_USER)
        ) {
            updateUser.setString(1, user.getFirstname());
            updateUser.setString(2, user.getLastname());
            updateUser.setString(3, user.getLogin());
            updateUser.setString(4, user.getEmail());
            updateUser.setString(5, user.getPhone());
            updateUser.setInt(6, user.getRole().ordinal() + ENUM_INDEX_DIFFERENCE);
            updateUser.setInt(7, user.getStatus().ordinal() + ENUM_INDEX_DIFFERENCE);
            updateUser.setString(8, user.getAbout());
            updateUser.setInt(9, user.getProfileImage().getId());
            updateUser.setInt(10, user.getId());
            updateUser.execute();
        } catch (SQLException e) {
            logger.error("Can't upload new user id= " + user.getId() + " data!", e);
            logger.debug(user);
            throw new DaoException("Can't upload new user data!");
        }
        logger.debug("User data successfully updated: " + user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> save(User user) throws DaoException {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_NEW_USER,
                        PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getFirstname());
            statement.setString(2, user.getLastname());
            statement.setString(3, user.getLogin());
            statement.setString(4, UNDEFINED_USER_PASSWORD);
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhone());
            statement.setInt(7, User.Role.USER.ordinal() + ENUM_INDEX_DIFFERENCE);
            statement.setInt(8, User.Status.DELAYED_REG.ordinal() + ENUM_INDEX_DIFFERENCE);
            statement.setString(9, user.getAbout());
            statement.setInt(10, DEFAULT_USER_IMAGE_ID);
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(ID_KEY));
                return Optional.of(user);
            } else {
                logger.error("Can't get generated keys from Result Set!");
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't save new user!", e);
            logger.debug(user);
            throw new DaoException("Can't save new user!", e);
        }
    }

    @Override
    public Optional<String> findEncryptedPassword(User user) throws DaoException {
        Optional<User> optionalUser = findUniqUser(user);
        if (!optionalUser.isPresent()) {
            return Optional.empty();
        }

        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PASSWORD_BY_USER_ID)) {

            User targetUser = optionalUser.get();
            statement.setInt(1, targetUser.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String pass = resultSet.getString(PASSWORD);
                return Optional.of(pass);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't find user's password: ", e);
            throw new DaoException("Can't find user's password", e);
        }
    }

    @Override
    public Optional<User> findUniqUser(User user) throws DaoException {
        int userId;
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = getSelectUserIdStatement(connection, user);
             ResultSet resultSet = statement.executeQuery()
        ) {

            if (resultSet.next()) {
                userId = resultSet.getInt(USER_ID);
            } else {
                return Optional.empty();
            }

        } catch (NullPointerException e) {
            logger.debug("Empty authentication field! Can't recognize user");
            return Optional.empty();
        } catch (SQLException e) {
            logger.error("Can't find user's password: ", e);
            throw new DaoException("Can't find user's password", e);
        }
        return findById(userId);
    }

    @Override
    public void updateUserPassword(User user, String hash) throws DaoException {
        Optional<User> toUpdate = findById(user.getId());
        if (!toUpdate.isPresent()) {
            logger.warn("User id=" + user.getId() + " is not exist!");
            return;
        }

        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PASSWORD)
        ) {
            statement.setString(1, hash);
            statement.setInt(2, user.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.error("Can't update password for user id=" + user.getId() + "!", e);
            throw new DaoException("Can't update user password!", e);
        }
    }

    private PreparedStatement getSelectUserIdStatement(Connection connection, User user) throws SQLException {
        String selectStatement;
        String identParam;
        if (user.getLogin() != null && !user.getLogin().isEmpty()) {
            selectStatement = SELECT_USER_ID_BY_LOGIN;
            identParam = user.getLogin();
        } else if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            selectStatement = SELECT_USER_ID_BY_EMAIL;
            identParam = user.getEmail();
        }else if (user.getPhone() != null && !user.getPhone().isEmpty()) {
            selectStatement = SELECT_USER_ID_BY_PHONE;
            identParam = user.getPhone();
        } else {
            logger.error("Can't find uniq field of provided user!");
            return null;
        }
        PreparedStatement statement = connection.prepareStatement(selectStatement);
        statement.setString(1, identParam);
        return statement;
    }

}