package by.epam.afc.service;

import by.epam.afc.controller.command.Pagination;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Announcement service.
 */
public interface AnnouncementService {
    /**
     * Find all announcements list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Announcement> findAll() throws ServiceException;

    /**
     * Save announcement optional.
     *
     * @param announcement the announcement
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Announcement> save(Announcement announcement) throws ServiceException;

    /**
     * Find announcements pagination.
     *
     * @param parameterMap the parameter map
     * @return the pagination
     * @throws ServiceException the service exception
     */
    Pagination<Announcement> findAnnouncements(Map<String, List<String>> parameterMap) throws ServiceException;

    /**
     * Find announcements pagination.
     *
     * @param parameterMap the parameter map
     * @param user         the user
     * @return the pagination
     * @throws ServiceException the service exception
     */
    Pagination<Announcement> findAnnouncements(Map<String, List<String>> parameterMap, User user) throws ServiceException;

    /**
     * Find announcement by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Announcement> findById(String id) throws ServiceException;

    /**
     * Update announcement optional.
     *
     * @param parameterMap the parameter map
     * @param user         the user
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Announcement> updateAnnouncement(Map<String, List<String>> parameterMap, User user) throws ServiceException;

    /**
     * Confirm announcement boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean confirmAnnouncement(int id) throws ServiceException;

    /**
     * Deactivate announcement boolean.
     *
     * @param id     the id
     * @param reason the reason
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deactivateAnnouncement(int id, String reason) throws ServiceException;

    /**
     * Change announcements status boolean.
     *
     * @param announcement the announcement
     * @param user         the user
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean changeAnnouncementsStatus(Announcement announcement, User user) throws ServiceException;
}
