package by.epam.afc;

import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.dao.impl.ImageDaoImpl;
import by.epam.afc.exception.DaoException;

public class Main {
    public static void main(String[] args) throws DaoException {
        ImageDaoImpl imageDao = DaoHolder.getImageDao();
        imageDao.findAll();
    }
}
