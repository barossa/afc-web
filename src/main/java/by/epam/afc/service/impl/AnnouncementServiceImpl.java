package by.epam.afc.service.impl;

import by.epam.afc.dao.AnnouncementDao;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.Image;
import by.epam.afc.dao.entity.User;
import by.epam.afc.dao.impl.AnnouncementDaoImpl;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.dao.impl.ImageDaoImpl;
import by.epam.afc.dao.impl.UserDaoImpl;
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

    private static final int PAGINATED_PAGE_ELEMENTS = 10;

    private AnnouncementServiceImpl() {
    }

    public static AnnouncementServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Announcement> findAll() throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            List<Announcement> announcements = announcementDao.findAll();
            return initializeLazyData(announcements);

        } catch (DaoException e) {
            logger.error("Can't load all announcements.", e);
            throw new ServiceException("Can't load all announcements.", e);
        }
    }

    @Override
    public List<Announcement> findByCategory(Category category) throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            List<Announcement> announcements = announcementDao.findByCategory(category);
            return initializeLazyData(announcements);

        } catch (DaoException e) {
            logger.error("Can't load announcements by category.", e);
            throw new ServiceException("Can't load announcements by category.", e);
        }
    }

    @Override
    public List<Announcement> findByName(String name) throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            List<Announcement> announcements = announcementDao.findByName(name);
            return initializeLazyData(announcements);

        } catch (DaoException e) {
            logger.error("Can't load announcements by name.", e);
            throw new ServiceException("Can't load announcements by name.", e);
        }
    }

    @Override
    public Optional<Announcement> save(Announcement announcement) throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            Optional<Announcement> saved = announcementDao.save(announcement);
            if (saved.isPresent()) {
                List<Image> images = announcement.getImages();
                List<Image> savedImages = saveImages(images);
                announcement.setImages(savedImages);

                if (savedImages.isEmpty()) {
                    logger.warn("No image saved with announcement!");
                    //// TODO: 9/29/21 ADD NO IMAGE PICTURE
                } else {
                    logger.debug("Saved " + savedImages.size() + " images with announcement");
                }

                return Optional.of(announcement);

            } else {
                logger.error("Can't save announcement data");
                throw new ServiceException("Can't save announcement data");

            }

        } catch (DaoException e) {
            logger.error("Can't create announcement.", e);
            throw new ServiceException("Can't create announcement.", e);
        }
    }

    @Override
    public List<Announcement> findAnnouncementsPage(int page, Status status) throws ServiceException {
        if (page < 0) {
            return new ArrayList<>();
        }

        try {
            AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
            int from = page * PAGINATED_PAGE_ELEMENTS;
            return announcementDao.findRange(from, from + PAGINATED_PAGE_ELEMENTS, status);
        } catch (DaoException e) {
            logger.error("Can't find paginated page", e);
            throw new ServiceException("Can't find paginated page", e);
        }
    }

    private List<Announcement> initializeLazyData(List<Announcement> announcements) throws DaoException {
        ImageDaoImpl imageDao = DaoHolder.getImageDao();
        UserDaoImpl userDao = DaoHolder.getUserDao();

        for (Announcement announcement : announcements) {
            List<Image> announcementImages = imageDao.findByAnnouncement(announcement);
            announcement.setImages(announcementImages);

            int ownerId = announcement.getOwner().getId();
            Optional<User> ownerOptional = userDao.findById(ownerId);
            User owner = ownerOptional.orElseThrow(DaoException::new);
            announcement.setOwner(owner);
        }
        return announcements;
    }

    private List<Image> saveImages(List<Image> images) throws ServiceException {
        ImageDaoImpl imageDao = DaoHolder.getImageDao();

        List<Image> savedImages = new ArrayList<>();
        for (Image image : images) {
            try {
                Optional<Image> optionalImage = imageDao.save(image);
                Image savedImage = optionalImage.orElseThrow(DaoException::new);
                savedImages.add(savedImage);

            } catch (DaoException e) {
                logger.error("Error occurred while saving images", e);
                throw new ServiceException("Error occurred while saving images", e);
            }
        }
        return savedImages;
    }
}
