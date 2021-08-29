package by.epam.afc.dao;

import by.epam.afc.dao.model.Announcement;
import by.epam.afc.dao.model.Image;
import by.epam.afc.exception.DaoException;

import java.util.List;

public interface ImageDao extends BaseDao<Image>{
    List<Image> findByAnnouncement(Announcement announcement) throws DaoException;
}
