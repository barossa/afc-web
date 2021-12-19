package by.epam.afc.service.validator;

import java.util.Map;

/**
 * The interface Announcement validator.
 */
public interface AnnouncementValidator {
    /**
     * Validate title boolean.
     *
     * @param title the title
     * @return the boolean
     */
    boolean validateTitle(String title);

    /**
     * Validate price boolean.
     *
     * @param price the price
     * @return the boolean
     */
    boolean validatePrice(String price);

    /**
     * Validate description boolean.
     *
     * @param description the description
     * @return the boolean
     */
    boolean validateDescription(String description);

    /**
     * Validate category boolean.
     *
     * @param category the category
     * @return the boolean
     */
    boolean validateCategory(String category);

    /**
     * Validate region boolean.
     *
     * @param region the region
     * @return the boolean
     */
    boolean validateRegion(String region);

    /**
     * Validate status boolean.
     *
     * @param status the status
     * @return the boolean
     */
    boolean validateStatus(String status);

    /**
     * Validate announcement data map.
     *
     * @param data the data
     * @return the map
     */
    Map<String, String> validateData(Map<String, String> data);
}
