package by.epam.afc.service;

import by.epam.afc.controller.command.Pagination;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(int id) throws ServiceException;
    Optional<User> authenticate(String authField, char[] password) throws ServiceException;
    Optional<User> register(Map<String, String> credentialsMap) throws ServiceException;
    Optional<User> activate(User user) throws ServiceException;
    Optional<User> updateCredentials(Map<String, String> credentials) throws ServiceException;
    Optional<User> updateMyCredentials(Map<String, String> credentials) throws ServiceException;
    boolean banUser(int id, String reason) throws ServiceException;
    boolean updatePassword(User user, char[] newPassword) throws ServiceException;
    boolean findLogin(String login) throws ServiceException;
    boolean findEmail(String email) throws ServiceException;
    boolean findPhone(String phone) throws ServiceException;
    Pagination<User> findUsers(Map<String, List<String>> parameterMap) throws ServiceException;
}
