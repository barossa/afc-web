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
import by.epam.afc.service.util.RequestParameterConverter;
import by.epam.afc.service.util.SearchHelper;
import by.epam.afc.service.validator.AnnouncementFilterValidator;
import by.epam.afc.service.validator.AnnouncementValidator;
import by.epam.afc.service.validator.ImageValidator;
import by.epam.afc.service.validator.NumberValidator;
import by.epam.afc.service.validator.impl.AnnouncementFilterValidatorImpl;
import by.epam.afc.service.validator.impl.AnnouncementValidatorImpl;
import by.epam.afc.service.validator.impl.ImageValidatorImpl;
import by.epam.afc.service.validator.impl.NumberValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.dao.entity.Announcement.Status.*;
import static by.epam.afc.service.validator.impl.CredentialsValidatorImpl.NOT_VALID;

/**
 * The type Announcement service.
 */
public class AnnouncementServiceImpl implements AnnouncementService {
    private static final AnnouncementServiceImpl instance = new AnnouncementServiceImpl();
    private static final Logger logger = LogManager.getLogger(AnnouncementServiceImpl.class);

    private static final int PAGINATED_PAGE_ELEMENTS = 5;

    private AnnouncementServiceImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AnnouncementServiceImpl getInstance() {
        return instance;
    }

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
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

    /**
     * Save optional.
     *
     * @param announcement the announcement
     * @return the optional
     * @throws ServiceException the service exception
     */
    @Override
    public Optional<Announcement> save(Announcement announcement) throws ServiceException {
        AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
        announcement.setPublicationDate(LocalDateTime.now());
        announcement.setStatus(MODERATING);
        try {
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

    /**
     * Find announcements pagination.
     *
     * @param parameterMap the parameter map
     * @return the pagination
     * @throws ServiceException the service exception
     */
    @Override
    public Pagination<Announcement> findAnnouncements(Map<String, List<String>> parameterMap) throws ServiceException {
        try {
            AnnouncementFilterValidator filterValidator = AnnouncementFilterValidatorImpl.getInstance();
            Map<String, List<String>> validatedParameters = filterValidator.validateParameterMap(parameterMap);
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

    /**
     * Find announcements pagination.
     *
     * @param parameterMap the parameter map
     * @param user         the user
     * @return the pagination
     * @throws ServiceException the service exception
     */
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

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
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

    /**
     * Update announcement optional.
     *
     * @param parameterMap the parameter map
     * @param user         the user
     * @return the optional
     * @throws ServiceException the service exception
     */
    @Override
    public Optional<Announcement> updateAnnouncement(Map<String, List<String>> parameterMap, User user) throws ServiceException {
        try {
            NumberValidator numberValidator = NumberValidatorImpl.getInstance();
            List<Image> validatedImages = parseImages(parameterMap.get(IMAGE));
            Optional<Integer> optionalId = parameterMap.get(ID).stream()
                    .filter(numberValidator::validateNumber)
                    .map(Integer::parseInt)
                    .findFirst();
            AnnouncementValidator announcementValidator = AnnouncementValidatorImpl.getInstance();
            RequestParameterConverter parameterConverter = RequestParameterConverter.getInstance();
            Map<String, String> singleValueParameters = parameterConverter.singleValueMap(parameterMap);
            Map<String, String> announcementData = announcementValidator.validateData(singleValueParameters);
            if (optionalId.isPresent() && !announcementData.containsValue(NOT_VALID)) {
                AnnouncementDaoImpl announcementDao = DaoHolder.getAnnouncementDao();
                Optional<Announcement> optionalAnnouncement = announcementDao.findById(optionalId.get());
                if (optionalAnnouncement.isPresent()) {
                    Announcement announcement = optionalAnnouncement.get();
                    User owner = announcement.getOwner();
                    if (user.getId() != owner.getId()) {
                        return Optional.empty();
                    }
                    validatedImages.forEach(image -> image.setUploadedBy(owner));
                    mapAnnouncement(announcement, announcementData);
                    if (!validatedImages.isEmpty()) {
                        announcement.setImages(validatedImages);
                        ImageDao imageDao = DaoHolder.getImageDao();
                        imageDao.saveAnnouncementImages(announcement);
                    }
                    announcement.setStatus(MODERATING);
                    return announcementDao.update(announcement);
                }
            }
            return Optional.empty();
        } catch (DaoException e) {
            logger.error("Can't update announcement info:", e);
            throw new ServiceException("Can't update announcement info", e);
        }
    }

    /**
     * Confirm announcement boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Override
    public boolean confirmAnnouncement(int id) throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            Optional<Announcement> announcementOptional = announcementDao.findById(id);
            if (!announcementOptional.isPresent()) {
                return false;
            }
            Announcement announcement = announcementOptional.get();
            if (announcement.getStatus() == MODERATING) {
                announcement.setStatus(ACTIVE);
                Optional<Announcement> optionalAnnouncement = announcementDao.update(announcement);
                return optionalAnnouncement.isPresent();
            } else {
                return false;
            }
        } catch (DaoException e) {
            logger.error("Can't confirm announcement:", e);
            throw new ServiceException("Can't confirm announcement", e);
        }
    }

    /**
     * Deactivate announcement boolean.
     *
     * @param id     the id
     * @param reason the reason
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Override
    public boolean deactivateAnnouncement(int id, String reason) throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            Optional<Announcement> announcementOptional = announcementDao.findById(id);
            AnnouncementValidator announcementValidator = AnnouncementValidatorImpl.getInstance();
            if (!announcementOptional.isPresent() || !announcementValidator.validateDescription(reason)) {
                return false;
            }
            Announcement announcement = announcementOptional.get();
            Announcement.Status status = announcement.getStatus();
            if (status == MODERATING || status == ACTIVE) {
                announcement.setStatus(INACTIVE);
                announcement.setDescription(reason);
                Optional<Announcement> optionalAnnouncement = announcementDao.update(announcement);
                return optionalAnnouncement.isPresent();
            } else {
                return false;
            }
        } catch (DaoException e) {
            logger.error("Can't deactivate announcement:", e);
            throw new ServiceException("Can't deactivate announcement", e);
        }
    }

    /**
     * Change announcements status boolean.
     *
     * @param announcement the announcement
     * @param user         the user
     * @return the boolean
     * @throws ServiceException the service exception
     */
    @Override
    public boolean changeAnnouncementsStatus(Announcement announcement, User user) throws ServiceException {
        try {
            AnnouncementDao announcementDao = DaoHolder.getAnnouncementDao();
            Optional<Announcement> announcementOptional = announcementDao.findById(announcement.getId());
            if (!announcementOptional.isPresent()) {
                return false;
            }
            Announcement oldAnnouncement = announcementOptional.get();
            User owner = oldAnnouncement.getOwner();
            if (owner.getId() == user.getId()) {
                oldAnnouncement.setStatus(announcement.getStatus());
                Optional<Announcement> updatedAnnouncement = announcementDao.update(oldAnnouncement);
                return updatedAnnouncement.isPresent();
            } else {
                return false;
            }
        } catch (DaoException e) {
            logger.error("Can't change announcement status:", e);
            throw new ServiceException("Can't change announcement status", e);
        }
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
                .allMatch(status -> announcement.getStatus().toString().equalsIgnoreCase(status));
        SearchHelper searchHelper = SearchHelper.getInstance();
        Predicate<Announcement> searchPredicate = announcement -> searches.stream()
                .map(searchHelper::completeRegex)
                .map(Pattern::compile)
                .map(pattern -> pattern.matcher(announcement.getTitle().toUpperCase()))
                .allMatch(Matcher::find);
        List<Announcement> filteredAnnouncements = announcements.stream()
                .filter(!regions.isEmpty() ? regionPredicate : a -> true)
                .filter(!categories.isEmpty() ? categoryPredicate : a -> true)
                .filter(!minPrices.isEmpty() ? minPricePredicate : a -> true)
                .filter(!maxPrices.isEmpty() ? maxPricePredicate : a -> true)
                .filter(!statuses.isEmpty() ? statusPredicate : a -> true)
                .filter(!searches.isEmpty() ? searchPredicate : a -> true)
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

    private void mapAnnouncement(Announcement announcement, Map<String, String> announcementData) {
        String title = announcementData.get(TITLE);
        int categoryId = Integer.parseInt(announcementData.get(CATEGORY));
        int regionId = Integer.parseInt(announcementData.get(REGION));
        Category category = new Category(categoryId);
        Region region = new Region(regionId);
        String description = announcementData.get(DESCRIPTION);
        BigDecimal price = new BigDecimal(announcementData.get(PRICE));
        announcement.setTitle(title);
        announcement.setCategory(category);
        announcement.setRegion(region);
        announcement.setDescription(description);
        announcement.setPrice(price);
    }

    private List<Image> parseImages(List<String> images) {
        ImageValidator imageValidator = ImageValidatorImpl.getInstance();
        ImageHelper imageHelper = ImageHelper.getInstance();
        if (images == null) {
            return new ArrayList<>();
        }
        List<Image> validatedImages = images.stream()
                .filter(imageValidator::validateImage)
                .map(imageHelper::fixImage)
                .map(image -> Image.getBuilder()
                        .uploadData(LocalDateTime.now())
                        .base64(image)
                        .build())
                .collect(Collectors.toList());
        return validatedImages;
    }
}
