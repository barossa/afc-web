package by.epam.afc.service;

import by.epam.afc.controller.command.pagination.AnnouncementPagination;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import static by.epam.afc.dao.entity.Announcement.Status;

public interface AnnouncementService {
    List<Announcement> findAll() throws ServiceException;
    /*List<Announcement> findByCategory(Category category) throws ServiceException;*/
/*    List<Announcement> findByName(String name) throws ServiceException;*/
    Optional<Announcement> save(Announcement announcement) throws ServiceException;
    Optional<AnnouncementPagination> findAnnouncements(Map<String, String[]> requestParameterMap) throws ServiceException;
    Optional<AnnouncementPagination> findAnnouncements(Map<String, String[]> requestParameterMap,
                                                       AnnouncementPagination pagination) throws ServiceException;
}
