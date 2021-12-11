package by.epam.afc.service;

import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String authField, char[] password) throws ServiceException;
    Optional<User> register(Map<String, String> credentialsMap) throws ServiceException;
    Optional<User> activate(User user) throws ServiceException;
    void updatePassword(User user, char[] newPassword) throws ServiceException;
    boolean findLogin(String login) throws ServiceException;
    boolean findEmail(String email) throws ServiceException;
    boolean findPhone(String phone) throws ServiceException;
}
