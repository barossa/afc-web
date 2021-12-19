package by.epam.afc.dao;

import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.DaoException;

import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDao extends BaseDao<User> {
    /**
     * Find user's encrypted password optional.
     *
     * @param user the user
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<String> findEncryptedPassword(User user) throws DaoException;

    /**
     * Find uniq user optional.
     *
     * @param user the user
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUniqUser(User user) throws DaoException;

    /**
     * Find user by login optional.
     *
     * @param login the login
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByLogin(String login) throws DaoException;

    /**
     * Find user by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByEmail(String email) throws DaoException;

    /**
     * Find user by phone optional.
     *
     * @param phone the phone
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findByPhone(String phone) throws DaoException;

    /**
     * Update user password boolean.
     *
     * @param user the user
     * @param hash the hash
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateUserPassword(User user, String hash) throws DaoException;
}
