package by.epam.afc.service.impl;

import by.epam.afc.controller.command.pagination.AnnouncementsPagination;
import by.epam.afc.dao.AnnouncementDao;
import by.epam.afc.dao.ImageDao;
import by.epam.afc.dao.entity.*;
import by.epam.afc.dao.impl.AnnouncementDaoImpl;
import by.epam.afc.dao.impl.DaoHolder;
import by.epam.afc.dao.impl.ImageDaoImpl;
import by.epam.afc.dao.impl.UserDaoImpl;
import by.epam.afc.exception.DaoException;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import by.epam.afc.service.util.ImageHelper;
import by.epam.afc.service.util.SearchHelper;
import by.epam.afc.service.validator.NumberValidator;
import by.epam.afc.service.validator.impl.NumberValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static by.epam.afc.dao.entity.Announcement.Status.ACTIVE;
import static by.epam.afc.dao.entity.Announcement.Status.UNDEFINED;

public class AnnouncementServiceImpl implements AnnouncementService {
    private static final AnnouncementServiceImpl instance = new AnnouncementServiceImpl();
    private final Logger logger = LogManager.getLogger(AnnouncementServiceImpl.class);

    private static final int PAGINATED_PAGE_ELEMENTS = 5;

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
            initializeLazyData(announcements);
            return announcements;

        } catch (DaoException e) {
            logger.error("Can't load all announcements.", e);
            throw new ServiceException("Can't load all announcements.", e);
        }
    }

    @Override
    public Optional<Announcement> save(Announcement announcement) throws ServiceException {
        AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
        announcement.setPublicationDate(LocalDateTime.now());
        announcement.setStatus(ACTIVE);

        try {
            int categoryId = announcement.getCategory().getId();
            int regionId = announcement.getRegion().getId();
            Optional<Category> categoryOptional = announcementDao.findCategory(categoryId);
            Optional<Region> regionOptional = announcementDao.findRegion(regionId);
            if (!categoryOptional.isPresent() || !regionOptional.isPresent()) {
                logger.debug("Submitted category or region is not presented!");
                return Optional.empty();
            }

            Optional<Announcement> announcementOptional = announcementDao.save(announcement);
            if (announcementOptional.isPresent()) {
                Announcement savedAnnouncement = announcementOptional.get();
                announcement.setId(savedAnnouncement.getId());

                ImageDao imageDao = DaoHolder.getImageDao();
                List<Image> savedImages = imageDao.saveAnnouncementImages(announcement);

                if (savedImages.isEmpty()) {
                    logger.warn("No image saved with announcement!");
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
    public Optional<AnnouncementsPagination> findAnnouncements(AnnouncementsPagination pagination) throws ServiceException {
        try {
            AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
            List<Announcement> announcements = announcementDao.findAll();
            return findPagination(announcements, pagination);

        } catch (DaoException e) {
            logger.error("Can't find paginated page", e);
            throw new ServiceException("Can't find paginated page", e);
        }
    }

    @Override
    public Optional<AnnouncementsPagination> findAnnouncements(AnnouncementsPagination pagination, User user) throws ServiceException {
        try {
            AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
            List<Announcement> announcements = announcementDao.findByOwner(user);
            return findPagination(announcements, pagination);

        } catch (DaoException e) {
            logger.error("Can't find paginated page", e);
            throw new ServiceException("Can't find paginated page", e);
        }
    }

    @Override
    public Optional<Announcement> findById(String id) throws ServiceException {
        try {
            NumberValidator validator = NumberValidatorImpl.getInstance();
            if (validator.validateNumber(id)) {
                AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
                Optional<Announcement> announcementOptional = announcementDao.findById(Integer.parseInt(id));
                if (announcementOptional.isPresent()) {
                    Announcement announcement = announcementOptional.get();
                    initializeLazyData(announcement);
                    return Optional.of(announcement);
                }
                return Optional.empty();
            }
        } catch (DaoException e) {
            logger.error("Can't find announcement:", e);
            throw new ServiceException("Can't find announcement:", e);
        }
        return Optional.empty();
    }

    private Optional<AnnouncementsPagination> findPagination(List<Announcement> announcements, AnnouncementsPagination pagination) throws DaoException {
        List<Announcement> filteredAnnouncements;
        filteredAnnouncements = filterData(announcements, pagination);
        int from = pagination.getCurrentPage() * PAGINATED_PAGE_ELEMENTS;
        int to = from + PAGINATED_PAGE_ELEMENTS - 1;
        int listSize = filteredAnnouncements.size();

        /*Getting actual data of current page*/
        if (listSize > from) {
            List<Announcement> currentAnnouncements;
            if (listSize > to) {
                currentAnnouncements = filteredAnnouncements.subList(from, to);
                currentAnnouncements.add(filteredAnnouncements.get(to));
            } else {
                currentAnnouncements = filteredAnnouncements.subList(from, listSize - 1);
                currentAnnouncements.add(filteredAnnouncements.get(listSize - 1));
            }
            initializeLazyData(currentAnnouncements);
            pagination.setCurrentData(currentAnnouncements);

        } else {
            pagination.setCurrentData(new ArrayList<>());
        }

        /*Is previous page available*/
        boolean isPreviousExists = pagination.getCurrentPage() > 0;
        pagination.setPrevious(isPreviousExists);

        /*Is at least one record above exists*/
        boolean isNextExists = listSize > to + 1;
        pagination.setNext(isNextExists);

        return Optional.of(pagination);
    }


    private List<Announcement> filterData(List<Announcement> announcements, AnnouncementsPagination pagination) {
        List<Category> categories = pagination.getCategories();
        List<Region> regions = pagination.getRegions();
        int rangeMin = pagination.getRangeMin();
        int rangeMax = pagination.getRangeMax();
        String search = pagination.getSearchRequest();

        Stream<Announcement> stream = announcements.stream();
        System.out.println("FILTERING DATA. INPUT: " + announcements.size());

        if (!categories.isEmpty()) {
            stream = stream.filter(ad -> categories.contains(ad.getCategory()));
        }
        if (!regions.isEmpty()) {
            stream = stream.filter(ad -> regions.contains(ad.getRegion()));
        }
        if (rangeMin > 0) {
            stream = stream.filter(ad -> ad.getPrice().intValue() > rangeMin);
        }
        if (rangeMax > 0) {
            stream = stream.filter(ad -> ad.getPrice().intValue() < rangeMax);
        }
        if (!search.isEmpty()) {
            SearchHelper searchHelper = SearchHelper.getInstance();
            String searchRegex = searchHelper.completeRegex(search);
            Pattern searchPattern = Pattern.compile(searchRegex);
            stream = stream.filter(ad -> searchPattern.matcher(ad.getTitle().toUpperCase()).find());
        }
        if (pagination.getStatus() != UNDEFINED) {
            stream = stream.filter(ad -> ad.getStatus() == pagination.getStatus());
        }

        List<Announcement> collect = stream.collect(Collectors.toList());
        System.out.println("FILTERING DATA. OUTPUT: " + collect.size());
        return collect;
    }

    private void initializeLazyData(List<Announcement> announcements) throws DaoException {
        for (Announcement announcement : announcements) {
            initializeLazyData(announcement);
        }
    }

    private void initializeLazyData(Announcement announcement) throws DaoException {
        ImageDaoImpl imageDao = DaoHolder.getImageDao();
        UserDaoImpl userDao = DaoHolder.getUserDao();

        List<Image> announcementImages = imageDao.findByAnnouncement(announcement);
        if (announcementImages.isEmpty()) {
            ImageHelper imageHelper = ImageHelper.getInstance();
            Image noImagePicture = imageHelper.getNoImagePicture();
            announcementImages.add(noImagePicture);
        }
        announcement.setImages(announcementImages);
        int ownerId = announcement.getOwner().getId();
        Optional<User> ownerOptional = userDao.findById(ownerId);
        User owner = ownerOptional.orElseThrow(DaoException::new);
        announcement.setOwner(owner);
    }
}
