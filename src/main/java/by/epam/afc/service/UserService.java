package by.epam.afc.service;

import by.epam.afc.controller.command.Pagination;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Find user by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findById(int id) throws ServiceException;

    /**
     * Authenticate user optional.
     *
     * @param authField the auth field
     * @param password  the password
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> authenticate(String authField, char[] password) throws ServiceException;

    /**
     * Register user optional.
     *
     * @param credentialsMap the credentials map
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> register(Map<String, String> credentialsMap) throws ServiceException;

    /**
     * Activate user optional.
     *
     * @param user the user
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> activate(User user) throws ServiceException;

    /**
     * Update user credentials optional.
     *
     * @param credentials the credentials
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> updateCredentials(Map<String, String> credentials) throws ServiceException;

    /**
     * Update my credentials optional.
     *
     * @param credentials the credentials
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> updateMyCredentials(Map<String, String> credentials) throws ServiceException;

    /**
     * Ban user boolean.
     *
     * @param id     the id
     * @param reason the reason
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean banUser(int id, String reason) throws ServiceException;

    /**
     * Update user password boolean.
     *
     * @param user        the user
     * @param newPassword the new password
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updatePassword(User user, char[] newPassword) throws ServiceException;

    /**
     * Find exists login boolean.
     *
     * @param login the login
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean findLogin(String login) throws ServiceException;

    /**
     * Find exists email boolean.
     *
     * @param email the email
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean findEmail(String email) throws ServiceException;

    /**
     * Find exists phone boolean.
     *
     * @param phone the phone
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean findPhone(String phone) throws ServiceException;

    /**
     * Find users pagination.
     *
     * @param parameterMap the parameter map
     * @return the pagination
     * @throws ServiceException the service exception
     */
    Pagination<User> findUsers(Map<String, List<String>> parameterMap) throws ServiceException;
}
