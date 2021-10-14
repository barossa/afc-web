package by.epam.afc.service.validator.impl;

import by.epam.afc.service.validator.AnnouncementFilterValidator;

import java.util.Arrays;
import java.util.Map;

import static by.epam.afc.controller.RequestAttribute.*;

public class AnnouncementFilterValidatorImpl implements AnnouncementFilterValidator {
    private static final AnnouncementFilterValidatorImpl instance = new AnnouncementFilterValidatorImpl();
    private static final NumberValidatorImpl numberValidator = NumberValidatorImpl.getInstance();

    private AnnouncementFilterValidatorImpl() {
    }

    public static AnnouncementFilterValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateRegion(String region) {
        if (region == null || region.isEmpty()) {
            return true;
        }
        return numberValidator.validateNumber(region);
    }

    @Override
    public boolean validateCategory(String category) {
        if (category == null || category.isEmpty()) {
            return true;
        }
        return numberValidator.validateNumber(category);
    }

    @Override
    public boolean validatePrice(String price) {
        if (price == null || price.isEmpty()) {
            return true;
        }
        return numberValidator.validateNumber(price);
    }

    @Override
    public boolean validateParameterMap(Map<String, String[]> parameterMap) {
        String[] regions = parameterMap.get(REGION);
        if (regions != null) {
            boolean valid = Arrays.stream(regions).allMatch(this::validateRegion);
            if (!valid) {
                return false;
            }
        }

        String[] categories = parameterMap.get(CATEGORY);
        if (categories != null) {
            boolean valid = Arrays.stream(categories).allMatch(this::validateCategory);
            if (!valid) {
                return false;
            }
        }

        String[] minPrices = parameterMap.get(PRICE_MIN);
        if (minPrices != null && !minPrices[0].isEmpty()) {
            boolean valid = numberValidator.validateNumber(minPrices[0]);
            if (!valid) {
                return false;
            }
        }

        String[] maxPrices = parameterMap.get(PRICE_MAX);
        if (maxPrices != null && !maxPrices[0].isEmpty()) {
            return numberValidator.validateNumber(maxPrices[0]);
        }
        return true;
    }
}
