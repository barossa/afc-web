package by.epam.afc.service.validator;

import java.util.List;
import java.util.Map;

/**
 * The interface Announcement filter validator.
 */
public interface AnnouncementFilterValidator {
    /**
     * Validate region boolean.
     *
     * @param region the region
     * @return the boolean
     */
    boolean validateRegion(String region);

    /**
     * Validate category boolean.
     *
     * @param category the category
     * @return the boolean
     */
    boolean validateCategory(String category);

    /**
     * Validate price boolean.
     *
     * @param price the price
     * @return the boolean
     */
    boolean validatePrice(String price);

    /**
     * Validate status boolean.
     *
     * @param status the status
     * @return the boolean
     */
    boolean validateStatus(String status);

    /**
     * Validate search boolean.
     *
     * @param search the search
     * @return the boolean
     */
    boolean validateSearch(String search);

    /**
     * Validate announcement parameter map map.
     *
     * @param parameterMap the parameter map
     * @return the map
     */
    Map<String, List<String>> validateParameterMap(Map<String, List<String>> parameterMap);
}
