package by.epam.afc.service.impl;

import by.epam.afc.dao.AnnouncementDao;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.impl.AnnouncementDaoImpl;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.exception.DaoException;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.epam.afc.dao.entity.Announcement.Status;

public class AnnouncementServiceImpl implements AnnouncementService {
    private static final AnnouncementServiceImpl instance = new AnnouncementServiceImpl();
    private final Logger logger = LogManager.getLogger(AnnouncementServiceImpl.class);

    private static final int PAGINATED_PAGE_ELEMENTS = 19;

    private AnnouncementServiceImpl(){}

    public static AnnouncementServiceImpl getInstance(){
        return instance;
    }

    @Override
    public List<Announcement> findAll() throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            return announcementDao.findAll();
        }catch (DaoException e){
            logger.error("Can't load all announcements.", e);
            throw new ServiceException("Can't load all announcements.", e);
        }
    }

    @Override
    public List<Announcement> findByCategory(Category category) throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            return announcementDao.findByCategory(category);
        }catch (DaoException e){
            logger.error("Can't load announcements by category.", e);
            throw new ServiceException("Can't load announcements by category.", e);
        }
    }

    @Override
    public List<Announcement> findByName(String name) throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            return announcementDao.findByName(name);
        }catch (DaoException e){
            logger.error("Can't load announcements by name.", e);
            throw new ServiceException("Can't load announcements by name.", e);
        }
    }

    @Override
    public Optional<Announcement> save(Announcement announcement) throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            return announcementDao.save(announcement);
        }catch (DaoException e){
            logger.error("Can't create announcement.", e);
            throw new ServiceException("Can't create announcement.", e);
        }
    }

    @Override
    public List<Announcement> findAnnouncementsPage(int page, Status status) throws ServiceException {
        if(page < 0){
            return new ArrayList<>();
        }

        try {
            AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
            int from = page * PAGINATED_PAGE_ELEMENTS;
            return announcementDao.findRange(from, from + PAGINATED_PAGE_ELEMENTS, status);
        }catch (DaoException e){
            logger.error("Can't find paginated page", e);
            throw new ServiceException("Can't find paginated page", e);
        }
    }
}
