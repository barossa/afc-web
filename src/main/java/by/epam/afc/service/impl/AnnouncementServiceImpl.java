package by.epam.afc.service.impl;

import by.epam.afc.controller.command.Pagination;
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
import by.epam.afc.service.util.PaginationHelper;
import by.epam.afc.service.util.SearchHelper;
import by.epam.afc.service.validator.AnnouncementFilterValidator;
import by.epam.afc.service.validator.NumberValidator;
import by.epam.afc.service.validator.SearchRequestValidator;
import by.epam.afc.service.validator.impl.AnnouncementFilterValidatorImpl;
import by.epam.afc.service.validator.impl.NumberValidatorImpl;
import by.epam.afc.service.validator.impl.SearchRequestValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.dao.entity.Announcement.Status.ACTIVE;
import static by.epam.afc.dao.entity.Announcement.Status.MODERATING;

public class AnnouncementServiceImpl implements AnnouncementService {
    private static final AnnouncementServiceImpl instance = new AnnouncementServiceImpl();
    private static final Logger logger = LogManager.getLogger(AnnouncementServiceImpl.class);

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
    public Pagination<Announcement> findAnnouncements(Map<String, List<String>> parameterMap) throws ServiceException {
        try {
            AnnouncementFilterValidator filterValidator = AnnouncementFilterValidatorImpl.getInstance();
            Map<String, List<String>> validatedParameters = filterValidator.validateParameterMap(parameterMap);
            List<String> searches = validateSearch(parameterMap.get(SEARCH));
            validatedParameters.put(SEARCH, searches);

            AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
            List<Announcement> allAnnouncements = announcementDao.findAll();
            List<Announcement> filteredAnnouncements = filterData(allAnnouncements, validatedParameters);

            PaginationHelper paginationHelper = PaginationHelper.getInstance();
            List<String> pages = parameterMap.get(PAGE);
            int page = paginationHelper.findPage(pages);

            Pagination<Announcement> pagination = paginationHelper.getPage(filteredAnnouncements, page, PAGINATED_PAGE_ELEMENTS);
            List<Announcement> announcements = pagination.getData();
            pagination.setRequestAttributes(validatedParameters);
            initializeLazyData(announcements);
            return pagination;

        } catch (DaoException e) {
            logger.error("Can't find paginated page", e);
            throw new ServiceException("Can't find paginated page", e);
        }
    }

    @Override
    public Pagination<Announcement> findAnnouncements(Map<String, List<String>> parameterMap, User user) throws ServiceException {
        try {
            AnnouncementFilterValidator filterValidator = AnnouncementFilterValidatorImpl.getInstance();
            Map<String, List<String>> validatedParameters = filterValidator.validateParameterMap(parameterMap);

            PaginationHelper paginationHelper = PaginationHelper.getInstance();
            List<String> pages = parameterMap.get(PAGE);
            int page = paginationHelper.findPage(pages);

            AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
            List<Announcement> allAnnouncements = announcementDao.findByOwner(user);
            List<Announcement> filteredAnnouncements = filterData(allAnnouncements, validatedParameters);

            Pagination<Announcement> pagination = paginationHelper.getPage(filteredAnnouncements, page, PAGINATED_PAGE_ELEMENTS);
            pagination.setRequestAttributes(validatedParameters);
            List<Announcement> announcements = pagination.getData();
            initializeLazyData(announcements);
            return pagination;

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

    @Override
    public boolean confirmAnnouncement(int id) throws ServiceException {
        try{
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            Optional<Announcement> announcementOptional = announcementDao.findById(id);
            if(!announcementOptional.isPresent()){
                return false;
            }
            Announcement announcement = announcementOptional.get();
            if(announcement.getStatus() == MODERATING){
                announcement.setStatus(ACTIVE);
                Optional<Announcement> optionalAnnouncement = announcementDao.update(announcement);
                return optionalAnnouncement.isPresent();
            }else{
                return false;
            }
        }catch (DaoException e){
            logger.error("Can't confirm announcement:", e);
            throw new ServiceException("Can't confirm announcement", e);
        }
    }

    private List<String> validateSearch(List<String> searches) {
        if (searches == null) {
            return new ArrayList<>();
        }
        SearchRequestValidator searchValidator = SearchRequestValidatorImpl.getInstance();
        List<String> validatedSearches = searches.stream()
                .filter(searchValidator::validateRequest)
                .collect(Collectors.toList());
        return validatedSearches;
    }

    private List<Announcement> filterData(List<Announcement> announcements, Map<String, List<String>> parameterMap) {
        List<Integer> regions = toIntList(parameterMap.get(REGION));
        List<Integer> categories = toIntList(parameterMap.get(CATEGORY));
        List<Integer> minPrices = toIntList(parameterMap.get(PRICE_MIN));
        List<Integer> maxPrices = toIntList(parameterMap.get(PRICE_MAX));
        List<String> searches = parameterMap.get(SEARCH);
        List<String> statuses = parameterMap.get(STATUS);
        Predicate<Announcement> regionPredicate = announcement -> regions.contains(announcement.getRegion().getId());
        Predicate<Announcement> categoryPredicate = announcement -> categories.contains(announcement.getCategory().getId());
        Predicate<Announcement> minPricePredicate = announcement -> minPrices.stream().allMatch(price -> announcement.getPrice().intValue() >= price);
        Predicate<Announcement> maxPricePredicate = announcement -> maxPrices.stream().allMatch(price -> announcement.getPrice().intValue() <= price);
        Predicate<Announcement> statusPredicate = announcement -> statuses.stream()
                .map(String::toUpperCase)
                .allMatch(status -> announcement.getStatus().toString().equals(status));
        SearchHelper searchHelper = SearchHelper.getInstance();
        Predicate<Announcement> searchPredicate = announcement -> searches.stream()
                .map(search -> searchHelper.completeRegex(search.toUpperCase()))
                .allMatch(regex -> announcement.getTitle().toUpperCase().matches(regex));

        List<Announcement> filteredAnnouncements = announcements.stream()
                .filter(!regions.isEmpty() ? regionPredicate : a -> true)
                .filter(!categories.isEmpty() ? categoryPredicate : a -> true)
                .filter(!minPrices.isEmpty() ? minPricePredicate : a -> true)
                .filter(!maxPrices.isEmpty() ? maxPricePredicate : a -> true)
                .filter(!statuses.isEmpty() ? statusPredicate : a -> true)
                .filter(searchPredicate)
                .collect(Collectors.toList());

        return filteredAnnouncements;
    }

    private List<Integer> toIntList(List<String> source) {
        return source.stream().map(Integer::parseInt).collect(Collectors.toList());
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
        int profileImageId = owner.getProfileImage().getId();
        Optional<Image> imageOptional = imageDao.findById(profileImageId);
        Image profileImage = imageOptional.orElseThrow(DaoException::new);
        owner.setProfileImage(profileImage);
        announcement.setOwner(owner);
    }
}
