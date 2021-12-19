package by.epam.afc.service.validator.impl;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.service.validator.AnnouncementValidator;
import by.epam.afc.service.validator.NumberValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Announcement validator.
 */
public class AnnouncementValidatorImpl implements AnnouncementValidator {
    private static final AnnouncementValidatorImpl instance = new AnnouncementValidatorImpl();

    /**
     * The constant TITLE.
     */
    public static final String TITLE = "title";
    /**
     * The constant DESCRIPTION.
     */
    public static final String DESCRIPTION = "description";
    /**
     * The constant PRICE.
     */
    public static final String PRICE = "price";
    /**
     * The constant CATEGORY.
     */
    public static final String CATEGORY = "category";
    /**
     * The constant REGION.
     */
    public static final String REGION = "region";

    private static final String TITLE_REGEX = "^(?=.*[A-Za-zА-Яа-я0-9\\-]+$)[A-Za-zА-Яа-я0-9\\-_]*$";
    private static final String DESCRIPTION_REGEX = "^(?!.*[<>;]+.*$)[A-Za-zА-Яа-я]+.*$";

    private static final int MAX_TITLE_LENGTH = 50;
    private static final int MAX_DESCRIPTION_LENGTH = 500;

    private static Pattern titlePattern;
    private static Pattern descriptionPattern;

    private AnnouncementValidatorImpl() {
        titlePattern = Pattern.compile(TITLE_REGEX);
        descriptionPattern = Pattern.compile(DESCRIPTION_REGEX);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AnnouncementValidatorImpl getInstance() {
        return instance;
    }

    /**
     * Validate title boolean.
     *
     * @param title the title
     * @return the boolean
     */
    @Override
    public boolean validateTitle(String title) {
        if (title != null) {
            if (title.length() > 0 && title.length() <= MAX_TITLE_LENGTH) {
                title = title.replace(" ", "_");
                Matcher matcher = titlePattern.matcher(title);
                return matcher.matches();
            }
        }
        return false;
    }

    /**
     * Validate price boolean.
     *
     * @param price the price
     * @return the boolean
     */
    @Override
    public boolean validatePrice(String price) {
        NumberValidator validator = NumberValidatorImpl.getInstance();
        return validator.validateNumber(price);
    }

    /**
     * Validate description boolean.
     *
     * @param description the description
     * @return the boolean
     */
    @Override
    public boolean validateDescription(String description) {
        if (description != null) {
            if (description.length() > 0 && description.length() <= MAX_DESCRIPTION_LENGTH) {
                Matcher matcher = descriptionPattern.matcher(description);
                return matcher.matches();
            }
        }
        return false;
    }

    /**
     * Validate category boolean.
     *
     * @param category the category
     * @return the boolean
     */
    @Override
    public boolean validateCategory(String category) {
        NumberValidator validator = NumberValidatorImpl.getInstance();
        return validator.validateNumber(category);
    }

    /**
     * Validate region boolean.
     *
     * @param region the region
     * @return the boolean
     */
    @Override
    public boolean validateRegion(String region) {
        NumberValidator validator = NumberValidatorImpl.getInstance();
        return validator.validateNumber(region);
    }

    /**
     * Validate status boolean.
     *
     * @param status the status
     * @return the boolean
     */
    @Override
    public boolean validateStatus(String status) {
        if (status != null) {
            try {
                Announcement.Status.valueOf(status.toUpperCase());
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Validate announcement data map.
     *
     * @param data the data
     * @return the map
     */
    @Override
    public Map<String, String> validateData(Map<String, String> data) {
        Map<String, String> correctData = new HashMap<>(data);
        if (!validateTitle(correctData.get(TITLE))) {
            correctData.put(TITLE, "");
        }
        if (!validatePrice(correctData.get(PRICE))) {
            correctData.put(PRICE, "");
        }
        if (!validateDescription(correctData.get(DESCRIPTION))) {
            correctData.put(DESCRIPTION, "");
        }
        if (!validateRegion(correctData.get(REGION))) {
            correctData.put(REGION, "");
        }
        if (!validateCategory(correctData.get(CATEGORY))) {
            correctData.put(CATEGORY, "");
        }
        return correctData;
    }
}
