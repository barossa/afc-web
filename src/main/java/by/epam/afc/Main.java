package by.epam.afc;

import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.dao.impl.UserDaoImpl;
import by.epam.afc.exception.DaoException;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.UserServiceImpl;

import java.awt.image.BufferedImage;
import java.util.Base64;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws DaoException, ServiceException {
        UserDaoImpl dao = DaoHolder.getUserDao();
        User user = User.getBuilder()
                .login("admin")
                .build();
        UserServiceImpl service = new UserServiceImpl();
        Optional<User> authenticate = service.authenticate(user, "admin".toCharArray());
        System.out.println(authenticate.isPresent());

    }
}
