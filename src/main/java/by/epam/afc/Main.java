package by.epam.afc;

import by.epam.afc.dao.ImageDao;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.exception.DaoException;
import by.epam.afc.exception.ServiceException;

import java.util.List;

public class Main {
    public static void main(String[] args) throws DaoException, ServiceException {
        ImageDao dao = DaoHolder.getImageDao();
        List<Image> byAnnouncement = dao.findByAnnouncement(Announcement.getBuilder().id(17).build());
        System.out.println(byAnnouncement.size());
    }
    }
