package by.epam.afc.service.validator;

import java.util.Map;

public interface AnnouncementFilterValidator {
    boolean validateRegion(String region);
    boolean validateCategory(String category);
    boolean validatePrice(String price);
    boolean validateParameterMap(Map<String, String[]> parameterMap);
}
