package by.epam.afc;

import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.impl.AnnouncementDaoImpl;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.dao.impl.UserDaoImpl;
import by.epam.afc.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws DaoException {
        UserDaoImpl userDao = DaoHolder.getUserDao();
        User user = User.getBuilder()
                .login("171")
                .phone("127312")
                .role(User.Role.USER)
                .status(User.Status.DELAYED_REG)
                .build();
        userDao.save(user);
    }
}
