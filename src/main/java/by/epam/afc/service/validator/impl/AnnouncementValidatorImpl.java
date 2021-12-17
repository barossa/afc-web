package by.epam.afc.service.validator.impl;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.service.validator.AnnouncementValidator;
import by.epam.afc.service.validator.NumberValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnnouncementValidatorImpl implements AnnouncementValidator {
    private static final AnnouncementValidatorImpl instance = new AnnouncementValidatorImpl();

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String CATEGORY = "category";
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

    public static AnnouncementValidatorImpl getInstance() {
        return instance;
    }

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

    @Override
    public boolean validatePrice(String price) {
        NumberValidator validator = NumberValidatorImpl.getInstance();
        return validator.validateNumber(price);
    }

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

    @Override
    public boolean validateCategory(String category) {
        NumberValidator validator = NumberValidatorImpl.getInstance();
        return validator.validateNumber(category);
    }

    @Override
    public boolean validateRegion(String region) {
        NumberValidator validator = NumberValidatorImpl.getInstance();
        return validator.validateNumber(region);
    }

    @Override
    public boolean validateStatus(String status) {
        if(status != null){
            try{
                Announcement.Status.valueOf(status.toUpperCase());
                return true;
            }catch (IllegalArgumentException e){
                return false;
            }
        }
        return false;
    }

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
