package by.epam.afc.dao;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface AnnouncementDao extends BaseDao<Announcement> {
    List<Announcement> findByOwner(User owner) throws DaoException;
    List<Announcement> findByCategory(Category category) throws DaoException;
    List<Announcement> findByName(String name) throws DaoException;
    List<Category> findAllCategories() throws DaoException;
    Optional<Category> findCategoryForName(String category) throws DaoException;
}
