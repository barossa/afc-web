package by.epam.afc.service;

import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String authField, char[] password) throws ServiceException;
    void updatePassword(User user, char[] newPassword) throws ServiceException;
}
