package by.epam.afc;

import by.epam.afc.dao.AnnouncementDao;
import by.epam.afc.dao.DialogDao;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Region;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.impl.AnnouncementDaoImpl;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.dao.impl.UserDaoImpl;
import by.epam.afc.exception.DaoException;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws DaoException, ServiceException {
        AnnouncementDao dao = DaoHolder.getAnnouncementDao();
        final List<Region> allRegions = dao.findAllRegions();
        allRegions.forEach(System.out::println);
    }
}
