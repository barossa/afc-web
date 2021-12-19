package by.epam.afc.dao;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.Region;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * The interface Announcement dao.
 */
public interface AnnouncementDao extends BaseDao<Announcement> {
    /**
     * Find announcement list by owner.
     *
     * @param owner the owner
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Announcement> findByOwner(User owner) throws DaoException;

    /**
     * Find all categories list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Category> findAllCategories() throws DaoException;

    /**
     * Find category optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Category> findCategory(int id) throws DaoException;

    /**
     * Find all regions list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Region> findAllRegions() throws DaoException;

    /**
     * Find region optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Region> findRegion(int id) throws DaoException;

}
