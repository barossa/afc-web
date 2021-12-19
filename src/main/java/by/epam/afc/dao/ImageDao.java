package by.epam.afc.dao;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Image;
import by.epam.afc.exception.DaoException;

import java.util.List;

/**
 * The interface Image dao.
 */
public interface ImageDao extends BaseDao<Image> {
    /**
     * Find images by announcement list.
     *
     * @param announcement the announcement
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Image> findByAnnouncement(Announcement announcement) throws DaoException;

    /**
     * Save announcement images list.
     *
     * @param announcement the announcement
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Image> saveAnnouncementImages(Announcement announcement) throws DaoException;
}
