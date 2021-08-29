package by.epam.afc.dao;

import by.epam.afc.dao.model.Announcement;
import by.epam.afc.dao.model.Announcement.*;
import by.epam.afc.dao.model.User;
import by.epam.afc.exception.DaoException;

import java.util.List;

public interface AnnouncementDao extends BaseDao<Announcement>{
    List<Announcement> findByOwner(User owner) throws DaoException;
    List<Announcement> findByCategory(Category category) throws DaoException;
}
