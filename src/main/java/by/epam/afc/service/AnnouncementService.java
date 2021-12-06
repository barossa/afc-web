package by.epam.afc.service;

import by.epam.afc.controller.command.Pagination;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AnnouncementService {
    List<Announcement> findAll() throws ServiceException;

    Optional<Announcement> save(Announcement announcement) throws ServiceException;

    Pagination<Announcement> findAnnouncements(Map<String, List<String>> parameterMap) throws ServiceException;

    Pagination<Announcement> findAnnouncements(Map<String, List<String>> parameterMap, User user) throws ServiceException;

    Optional<Announcement> findById(String id) throws ServiceException;
}
