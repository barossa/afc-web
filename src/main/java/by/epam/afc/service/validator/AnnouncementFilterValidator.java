package by.epam.afc.service.validator;

import java.util.List;
import java.util.Map;

public interface AnnouncementFilterValidator {
    boolean validateRegion(String region);
    boolean validateCategory(String category);
    boolean validatePrice(String price);
    boolean validateStatus(String status);
    boolean validateSearch(String search);
    Map<String, List<String>> validateParameterMap(Map<String, List<String>> parameterMap);
}
