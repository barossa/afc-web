package by.epam.afc.service.impl;

import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.dao.impl.UserDaoImpl;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.DaoException;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.PasswordCryptor;
import by.epam.afc.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public Optional<User> authenticate(User user, char[] password) throws ServiceException {
        UserDaoImpl userDao = DaoHolder.getUserDao();
        String encrypted = PasswordCryptor.encrypt(password);
        try {
            return userDao.authenticate(user, encrypted);
        } catch (DaoException e) {
            logger.error("Can't authenticate user: ", e);
            throw new ServiceException("Can't authenticate user: ", e);
        }
    }

    @Override
    public void updatePassword(User user, char[] newPassword) throws ServiceException {
        UserDaoImpl userDao = DaoHolder.getUserDao();
        String encrypted = PasswordCryptor.encrypt(newPassword);
        try {
            userDao.updateUserPassword(user, encrypted);
        } catch (DaoException e) {
            logger.error("can't update user id= " + user.getId() + " password: ", e);
            throw new ServiceException("Can't update user password: ", e);
        }
    }
}
