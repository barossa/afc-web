package by.epam.afc.tag;

import by.epam.afc.controller.command.Pagination;
import by.epam.afc.dao.entity.Announcement;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static by.epam.afc.controller.RequestAttribute.LOCALE;
import static by.epam.afc.controller.RequestAttribute.PAGINATION;

/**
 * The type Tag utils.
 */
public final class TagUtils {
    private static final String BUNDLE_BASENAME = "prop.pagecontent";
    private static final String LOCALE_SPLITTER = "_";
    private static final String TO_BE_CONTINUED = "...";
    private static final int LANG_KEY = 0;
    private static final int COUNTRY_KEY = 1;
    private static final int SHORT_DESCRIPTION_LENGTH = 50;

    private TagUtils() {
    }

    /**
     * Find bundle resource bundle.
     *
     * @param session the session
     * @return the resource bundle
     */
    public static ResourceBundle findBundle(HttpSession session) {
        String locale = (String) session.getAttribute(LOCALE);
        String[] tags = locale.split(LOCALE_SPLITTER);
        Locale currentLocale = new Locale(tags[LANG_KEY], tags[COUNTRY_KEY]);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_BASENAME, currentLocale);
        return resourceBundle;
    }

    /**
     * Find attributes map.
     *
     * @param request the request
     * @return the map
     */
    public static Map<String, List<String>> findAttributes(ServletRequest request) {
        Pagination pagination = (Pagination) request.getAttribute(PAGINATION);
        Map<String, List<String>> requestAttributes = pagination.getRequestAttributes();
        return requestAttributes;
    }


    /**
     * Gets short description.
     *
     * @param announcement the announcement
     * @return the short description
     */
    public static String getShortDescription(Announcement announcement) {
        String description = announcement.getDescription();
        if (description == null || description.isEmpty()) {
            return "";
        }
        String shortDescription;
        if (description.length() <= SHORT_DESCRIPTION_LENGTH) {
            shortDescription = description;
        } else {
            shortDescription = description.substring(0, SHORT_DESCRIPTION_LENGTH) + TO_BE_CONTINUED;
        }
        return shortDescription;
    }
}
