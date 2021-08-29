package by.epam.afc.dao;

import by.epam.afc.dao.model.User;
import by.epam.afc.exception.DaoException;

import java.util.Optional;

public interface UserDao extends BaseDao<User>{
    Optional<String> findUserPassword(User user) throws DaoException;
    void updateUserPassword(User user, String hash) throws DaoException;
}
