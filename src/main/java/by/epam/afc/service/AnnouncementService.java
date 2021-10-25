package by.epam.afc.service;

import by.epam.afc.controller.command.pagination.AnnouncementsPagination;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    List<Announcement> findAll() throws ServiceException;

    Optional<Announcement> save(Announcement announcement) throws ServiceException;

    Optional<AnnouncementsPagination> findAnnouncements(AnnouncementsPagination pagination) throws ServiceException;

    Optional<AnnouncementsPagination> findAnnouncements(AnnouncementsPagination pagination, User user) throws ServiceException;
}
