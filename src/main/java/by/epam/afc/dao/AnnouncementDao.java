package by.epam.afc.dao;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.Region;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.DaoException;

import java.util.List;
import java.util.Optional;

import static by.epam.afc.dao.entity.Announcement.Status;

public interface AnnouncementDao extends BaseDao<Announcement> {
    List<Announcement> findByOwner(User owner) throws DaoException;
    /*List<Announcement> findByCategory(Category category) throws DaoException;*/
/*    List<Announcement> findByName(String name) throws DaoException;*/
/*    List<Announcement> findRange(int from, int to, Status status) throws DaoException;*/

    List<Category> findAllCategories() throws DaoException;
    Optional<Category> findCategory(String category) throws DaoException;
    Optional<Category> findCategory(int id) throws DaoException;

    List<Region> findAllRegions() throws DaoException;
    Optional<Region> findRegion(int id) throws DaoException;

}
