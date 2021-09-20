package by.epam.afc.service;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.exception.ServiceException;

import java.util.List;

public interface AnnouncementService {
    List<Announcement> findAll() throws ServiceException;
    List<Announcement> findByCategory(Category category) throws ServiceException;
    List<Announcement> findByName(String name) throws ServiceException;
}
