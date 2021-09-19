package by.epam.afc;

import by.epam.afc.dao.DialogDao;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.dao.impl.UserDaoImpl;
import by.epam.afc.exception.DaoException;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.UserServiceImpl;

import java.util.Optional;

public class Main {
    public static void main(String[] args) throws DaoException, ServiceException {
        DialogDao dao = DaoHolder.getDialogDao();
        dao.findByUser(User.getBuilder().id(0).build());

    }
}
