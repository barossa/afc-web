package by.epam.afc.dao.impl;

import by.epam.afc.dao.UserDao;
import by.epam.afc.dao.entity.User;
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

/**
 * The type User dao.
 */
public class UserDaoImpl implements UserDao {

    private static final String SELECT_ALL_USERS = "SELECT " + USER_ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", "
            + LOGIN + ", " + EMAIL + ", " + PHONE + ", " + ROLE_DESCRIPTION + ", " + STATUS_DESCRIPTION + ", "
            + ABOUT + ", " + PROFILE_IMAGE_ID
            + " FROM " + USERS
            + " INNER JOIN " + USER_STATUSES + " ON " + USERS + "." + STATUS_ID + "=" + USER_STATUSES + "." + STATUS_ID
            + " INNER JOIN " + USER_ROLES + " ON " + USERS + "." + ROLE_ID + "=" + USER_ROLES + "." + ROLE_ID + ";";

    private static final String SELECT_BY_ID = "SELECT " + USER_ID + ", " + FIRST_NAME + ", " + LAST_NAME + ", "
            + LOGIN + ", " + EMAIL + ", " + PHONE + ", " + ROLE_DESCRIPTION + ", " + STATUS_DESCRIPTION + ", "
            + ABOUT + ", " + PROFILE_IMAGE_ID
            + " FROM " + USERS
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
    private static final int DEFAULT_USER_IMAGE_ID = 3;

    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    private final ConnectionPool pool = ConnectionPool.getInstance();

    /**
     * Instantiates a new User dao.
     */
    UserDaoImpl() {
    }

    /**
     * Find all users list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    @Override
    public List<User> findAll() throws DaoException {
        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
                ResultSet resultSet = statement.executeQuery()
        ) {

            List<User> users = new ArrayList<>();
            UserRowMapper userMapper = UserRowMapper.getInstance();
            while (resultSet.next()) {
                User user = userMapper.mapRows(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.error("Error occurred while loading all users", e);
            throw new DaoException("Error occurred while loading all users", e);
        }
    }

    /**
     * Find user by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
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
            UserRowMapper userMapper = UserRowMapper.getInstance();
            User user = userMapper.mapRows(resultSet);
            return Optional.of(user);

        } catch (SQLException e) {
            logger.error("Can't find user by ID", e);
            throw new DaoException("Can't find user by ID", e);
        }
    }

    /**
     * Update user optional.
     *
     * @param user the user
     * @return the optional
     * @throws DaoException the dao exception
     */
    @Override
    public Optional<User> update(User user) throws DaoException {
        Optional<User> toUpdate = findById(user.getId());
        if (!toUpdate.isPresent()) {
            logger.warn("User id=" + user.getId() + " is not presented for update!");
            return Optional.empty();
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
            updateUser.setInt(6, user.getRole().ordinal());
            updateUser.setInt(7, user.getStatus().ordinal());
            updateUser.setString(8, user.getAbout());
            updateUser.setInt(9, user.getProfileImage().getId());
            updateUser.setInt(10, user.getId());
            updateUser.execute();
            return Optional.of(user);
        } catch (SQLException e) {
            logger.error("Can't upload new user id= " + user.getId() + " data!", e);
            logger.debug(user);
            throw new DaoException("Can't upload new user data!");
        }
    }

    /**
     * Save user optional.
     *
     * @param user the user
     * @return the optional
     * @throws DaoException the dao exception
     */
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
            statement.setInt(7, user.getRole().ordinal());
            statement.setInt(8, user.getStatus().ordinal());
            statement.setString(9, user.getAbout());
            statement.setInt(10, DEFAULT_USER_IMAGE_ID);
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(ID_KEY);
                return findById(userId);
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

    /**
     * Find user's encrypted password optional.
     *
     * @param user the user
     * @return the optional
     * @throws DaoException the dao exception
     */
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

    /**
     * Find uniq user optional.
     *
     * @param user the user
     * @return the optional
     * @throws DaoException the dao exception
     */
    @Override
    public Optional<User> findUniqUser(User user) throws DaoException {
        String login = user.getLogin();
        String email = user.getEmail();
        String phone = user.getPhone();

        if (login != null && !login.isEmpty()) {
            return findByLogin(login);
        } else if (email != null && !email.isEmpty()) {
            return findByEmail(email);
        } else if (phone != null && !phone.isEmpty()) {
            return findByPhone(phone);
        } else {
            logger.error("Can't find uniq field of provided user!");
            return Optional.empty();
        }
    }

    /**
     * Find user by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    @Override
    public Optional<User> findByLogin(String login) throws DaoException {
        Optional<Integer> optionalUserId = findUserIdByLogin(login);
        if (!optionalUserId.isPresent()) {
            return Optional.empty();
        }
        int userId = optionalUserId.get();
        return findById(userId);
    }

    /**
     * Find user by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        Optional<Integer> optionalUserId = findUserIdByEmail(email);
        if (!optionalUserId.isPresent()) {
            return Optional.empty();
        }
        int userId = optionalUserId.get();
        return findById(userId);
    }

    /**
     * Find user by phone optional.
     *
     * @param phone the phone
     * @return the optional
     * @throws DaoException the dao exception
     */
    @Override
    public Optional<User> findByPhone(String phone) throws DaoException {
        Optional<Integer> optionalUserId = findUserIdByPhone(phone);
        if (!optionalUserId.isPresent()) {
            return Optional.empty();
        }
        int userId = optionalUserId.get();
        return findById(userId);
    }

    /**
     * Update user password boolean.
     *
     * @param user the user
     * @param hash the hash
     * @return the boolean
     * @throws DaoException the dao exception
     */
    @Override
    public boolean updateUserPassword(User user, String hash) throws DaoException {
        Optional<User> toUpdate = findById(user.getId());
        if (!toUpdate.isPresent()) {
            logger.warn("User id=" + user.getId() + " is not exist!");
            return false;
        }

        try (
                Connection connection = pool.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER_PASSWORD)
        ) {
            statement.setString(1, hash);
            statement.setInt(2, user.getId());
            return statement.execute();
        } catch (SQLException e) {
            logger.error("Can't update password for user id=" + user.getId() + "!", e);
            throw new DaoException("Can't update user password!", e);
        }
    }

    private Optional<Integer> findUserIdByLogin(String login) throws DaoException {
        return findUserId(login, SELECT_USER_ID_BY_LOGIN);
    }

    private Optional<Integer> findUserIdByEmail(String email) throws DaoException {
        return findUserId(email, SELECT_USER_ID_BY_EMAIL);
    }

    private Optional<Integer> findUserIdByPhone(String phone) throws DaoException {
        return findUserId(phone, SELECT_USER_ID_BY_PHONE);
    }

    private Optional<Integer> findUserId(String identField, String selectQuery) throws DaoException {
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {
            statement.setString(1, identField);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt(USER_ID);
                return Optional.of(userId);
            } else {
                return Optional.empty();
            }

        } catch (SQLException e) {
            logger.error("Can't find user id: ", e);
            throw new DaoException("Can't find user id", e);
        }
    }

}