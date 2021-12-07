package by.epam.afc.service.validator.impl;

import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.service.validator.AnnouncementFilterValidator;
import by.epam.afc.service.validator.NumberValidator;
import by.epam.afc.service.validator.SearchRequestValidator;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static by.epam.afc.controller.RequestAttribute.*;

public class AnnouncementFilterValidatorImpl implements AnnouncementFilterValidator {
    private static final AnnouncementFilterValidatorImpl instance = new AnnouncementFilterValidatorImpl();
    private static final NumberValidator numberValidator = NumberValidatorImpl.getInstance();
    private static final SearchRequestValidator searchValidator = SearchRequestValidatorImpl.getInstance();

    private AnnouncementFilterValidatorImpl() {
    }

    public static AnnouncementFilterValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateRegion(String region) {
        if (region == null || region.isEmpty()) {
            return false;
        }
        return numberValidator.validateNumber(region);
    }

    @Override
    public boolean validateCategory(String category) {
        if (category == null || category.isEmpty()) {
            return false;
        }
        return numberValidator.validateNumber(category);
    }

    @Override
    public boolean validatePrice(String price) {
        if (price == null || price.isEmpty()) {
            return false;
        }
        return numberValidator.validateNumber(price);
    }

    @Override
    public boolean validateStatus(String status) {
        if (status == null || status.isEmpty()) {
            return false;
        }
        List<String> statuses = Arrays.stream(Announcement.Status.values())
                .map(Enum::toString)
                .collect(Collectors.toList());
        return statuses.contains(status.toUpperCase());
    }

    @Override
    public boolean validateSearch(String search) {
        if(search == null || search.isEmpty()){
            return false;
        }
        return searchValidator.validateRequest(search);
    }

    @Override
    public Map<String, List<String>> validateParameterMap(Map<String, List<String>> parameterMap) {
        Map<String, List<String>> requestParams = new HashMap<>();
        List<String> regions = parameterMap.get(REGION);
        List<String> categories = parameterMap.get(CATEGORY);
        List<String> minPrices = parameterMap.get(PRICE_MIN);
        List<String> maxPrices = parameterMap.get(PRICE_MAX);
        List<String> statuses = parameterMap.get(STATUS);
        List<String> searches = parameterMap.get(SEARCH);
        requestParams.put(REGION, validateParam(regions, this::validateRegion));
        requestParams.put(CATEGORY, validateParam(categories, this::validateCategory));
        requestParams.put(PRICE_MIN, validateParam(minPrices, this::validatePrice));
        requestParams.put(PRICE_MAX, validateParam(maxPrices, this::validatePrice));
        requestParams.put(STATUS, validateParam(statuses, this::validateStatus));
        requestParams.put(SEARCH, validateParam(searches, this::validateSearch));
        return requestParams;
    }

    private List<String> validateParam(List<String> params, Predicate<String> validator) {
        if (params == null) {
            return new ArrayList<>();
        }
        return params.stream().filter(validator).collect(Collectors.toList());
    }
}
