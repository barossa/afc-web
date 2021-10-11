package by.epam.afc.service.validator;

import java.util.Map;

public interface AnnouncementValidator {
    boolean validateTitle(String title);
    boolean validatePrice(String price);
    boolean validateDescription(String description);
    boolean validateCategory(String category);
    boolean validateRegion(String region);
    Map<String, String> validateData(Map<String, String> data);
}
