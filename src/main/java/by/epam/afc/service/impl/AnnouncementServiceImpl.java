package by.epam.afc.service.impl;

import by.epam.afc.controller.command.pagination.AnnouncementPagination;
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
import by.epam.afc.service.validator.AnnouncementValidator;
import by.epam.afc.service.validator.SearchRequestValidator;
import by.epam.afc.service.validator.impl.AnnouncementValidatorImpl;
import by.epam.afc.service.validator.impl.SearchRequestValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.dao.entity.Announcement.Status.ACTIVE;

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

    /*@Override
    public List<Announcement> findByCategory(Category category) throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            List<Announcement> announcements = announcementDao.findByCategory(category);
            return initializeLazyData(announcements);

        } catch (DaoException e) {
            logger.error("Can't load announcements by category.", e);
            throw new ServiceException("Can't load announcements by category.", e);
        }
    }*/

    /*@Override
    public List<Announcement> findByName(String name) throws ServiceException {
        try {
            // TODO: 10/10/21 REFACTOR
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            List<Announcement> announcements = announcementDao.findByName(name);
            initializeLazyData(announcements);
            return announcements;

        } catch (DaoException e) {
            logger.error("Can't load announcements by name.", e);
            throw new ServiceException("Can't load announcements by name.", e);
        }
    }*/

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
    public Optional<AnnouncementPagination> findAnnouncements(Map<String, String[]> requestParameterMap) throws ServiceException {
        if (!validateParameterMap(requestParameterMap)) {
            return Optional.empty();
        }
        AnnouncementPagination pagination = parseRequestMap(requestParameterMap);
        pagination.setCurrentPage(0);


        return Optional.of(pagination);
    }

    @Override
    public Optional<AnnouncementPagination> findAnnouncements(Map<String, String[]> requestParameterMap,
                                                              AnnouncementPagination pagination) throws ServiceException {
        if (!validateParameterMap(requestParameterMap)) {
            return Optional.empty();
        }

        AnnouncementPagination freshPagination = parseRequestMap(requestParameterMap);
        if (freshPagination.equals(pagination)) {
            if (pagination.isNext()) {
                findAnnouncementsPage(pagination);
            } else {
                return Optional.empty();
            }
            return Optional.of(pagination);
        } else {
            freshPagination.setCurrentPage(0);
            findAnnouncementsPage(freshPagination);
            return Optional.of(freshPagination);
        }
    }

    private void findAnnouncementsPage(AnnouncementPagination pagination) throws ServiceException {
        try {
            AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
            List<Announcement> announcements = announcementDao.findAll();
            List<Announcement> filteredAnnouncements = filterData(announcements, pagination);

            int from = pagination.getCurrentPage() * PAGINATED_PAGE_ELEMENTS;
            int to = from + PAGINATED_PAGE_ELEMENTS;
            int listSize = filteredAnnouncements.size();

            /*Getting actual data of current page*/
            if (listSize > from) {
                List<Announcement> currentAnnouncements;
                if (listSize > to) {
                    currentAnnouncements = filteredAnnouncements.subList(from, to);
                } else {
                    currentAnnouncements = filteredAnnouncements.subList(from, listSize - 1);
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

        } catch (DaoException e) {
            logger.error("Can't find paginated page", e);
            throw new ServiceException("Can't find paginated page", e);
        }
    }

    private List<Announcement> filterData(List<Announcement> announcements, AnnouncementPagination pagination) {
        List<Category> categories = pagination.getCategories();
        List<Region> regions = pagination.getRegions();
        int rangeMin = pagination.getRangeMin();
        int rangeMax = pagination.getRangeMax();
        String search = pagination.getSearchRequest();

        Stream<Announcement> stream = announcements.stream();

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
            stream = stream.filter(ad -> searchPattern.matcher(ad.getTitle()).find());
        }

        return stream.collect(Collectors.toList());
    }

    private void initializeLazyData(List<Announcement> announcements) throws DaoException {
        ImageDaoImpl imageDao = DaoHolder.getImageDao();
        UserDaoImpl userDao = DaoHolder.getUserDao();

        for (Announcement announcement : announcements) {
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

    private boolean validateParameterMap(Map<String, String[]> requestParameterMap) {
        AnnouncementValidator validator = AnnouncementValidatorImpl.getInstance();

        String[] regions = requestParameterMap.get(REGION);
        String[] categories = requestParameterMap.get(CATEGORY);
        regions = initializeParameter(regions);
        categories = initializeParameter(categories);

        boolean isRegionsCorrect = Arrays.stream(regions).allMatch(validator::validateRegion);
        boolean isCategoriesCorrect = Arrays.stream(categories).allMatch(validator::validateCategory);
        if (!isRegionsCorrect || !isCategoriesCorrect) {
            return false;
        }

        String[] priceMin = requestParameterMap.get(PRICE_MIN);
        priceMin = initializeParameter(priceMin);
        boolean isMinPriceCorrect = priceMin.length == 0 || validator.validatePrice(priceMin[0]);

        String[] priceMax = requestParameterMap.get(PRICE_MAX);
        priceMax = initializeParameter(priceMax);
        boolean isMaxPriceCorrect = priceMax.length == 0 || validator.validatePrice(priceMax[0]);
        if (!isMinPriceCorrect || !isMaxPriceCorrect) {
            return false;
        }


        String[] searchRequest = requestParameterMap.get(SEARCH);
        searchRequest = initializeParameter(searchRequest);
        SearchRequestValidator requestValidator = SearchRequestValidatorImpl.getInstance();
        return searchRequest.length == 0 || requestValidator.validateRequest(searchRequest[0]);

    }

    private String[] initializeParameter(String[] parameter){
        if(parameter == null) {
            return new String[0];
        }
        return parameter;
    }

    private AnnouncementPagination parseRequestMap(Map<String, String[]> requestParameterMap) {
        String[] regions = requestParameterMap.get(REGION);
        String[] categories = requestParameterMap.get(CATEGORY);
        String[] minPrices = requestParameterMap.get(PRICE_MIN);
        String[] maxPrices = requestParameterMap.get(PRICE_MAX);
        String[] searchRequests = requestParameterMap.get(SEARCH);

        List<Region> collectedRegions = Arrays.stream(regions)
                .mapToInt(Integer::parseInt)
                .mapToObj(Region::new)
                .collect(Collectors.toList());

        List<Category> collectedCategories = Arrays.stream(categories)
                .mapToInt(Integer::parseInt)
                .mapToObj(Category::new)
                .collect(Collectors.toList());

        int minPrice = (minPrices[0].isEmpty() ? 0 : Integer.parseInt(minPrices[0]));
        int maxPrice = (maxPrices[0].isEmpty() ? 0 : Integer.parseInt(maxPrices[0]));

        AnnouncementPagination pagination = new AnnouncementPagination(collectedRegions, collectedCategories,
                minPrice, maxPrice, searchRequests[0], ACTIVE);
        return pagination;
    }
}
