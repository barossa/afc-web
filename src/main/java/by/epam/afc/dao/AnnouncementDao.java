package by.epam.afc.dao;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.Region;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface AnnouncementDao extends BaseDao<Announcement> {
    List<Announcement> findByOwner(User owner) throws DaoException;

    List<Category> findAllCategories() throws DaoException;

    Optional<Category> findCategory(int id) throws DaoException;

    List<Region> findAllRegions() throws DaoException;

    Optional<Region> findRegion(int id) throws DaoException;

}
