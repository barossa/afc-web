package by.epam.afc.service.impl;

import by.epam.afc.controller.command.pagination.AnnouncementsPagination;
import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.Region;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.dao.entity.Announcement.Status.ACTIVE;

public class AnnouncementFilterParser {
    private static final AnnouncementFilterParser instance = new AnnouncementFilterParser();

    private AnnouncementFilterParser(){}

    public static AnnouncementFilterParser getInstance() {
        return instance;
    }

    public AnnouncementsPagination parseFilter(Map<String, String[]> requestParameterMap) {
        String[] regions = requestParameterMap.get(REGION);
        String[] categories = requestParameterMap.get(CATEGORY);
        String[] minPrices = requestParameterMap.get(PRICE_MIN);
        String[] maxPrices = requestParameterMap.get(PRICE_MAX);
        String[] searchRequests = requestParameterMap.get(SEARCH);

        List<Region> collectedRegions;
        if(regions != null) {
            collectedRegions = Arrays.stream(regions)
                    .mapToInt(Integer::parseInt)
                    .mapToObj(Region::new)
                    .collect(Collectors.toList());
        }else{
            collectedRegions = new ArrayList<>();
        }

        List<Category> collectedCategories;
        if(categories != null) {
            collectedCategories = Arrays.stream(categories)
                    .mapToInt(Integer::parseInt)
                    .mapToObj(Category::new)
                    .collect(Collectors.toList());
        }else{
            collectedCategories = new ArrayList<>();
        }

        int minPrice = (minPrices == null || minPrices[0].isEmpty() ? 0 : Integer.parseInt(minPrices[0]));
        int maxPrice = (maxPrices == null || maxPrices[0].isEmpty() ? 0 : Integer.parseInt(maxPrices[0]));

        String searchRequest = (searchRequests == null || searchRequests[0].isEmpty() ? "" : searchRequests[0]);

        AnnouncementsPagination pagination = new AnnouncementsPagination(collectedRegions, collectedCategories,
                minPrice, maxPrice, searchRequest, ACTIVE);
        return pagination;
    }
}
