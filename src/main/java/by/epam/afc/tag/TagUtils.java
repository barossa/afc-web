package by.epam.afc.tag;

import by.epam.afc.controller.command.Pagination;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static by.epam.afc.controller.RequestAttribute.LOCALE;
import static by.epam.afc.controller.RequestAttribute.PAGINATION;

public final class TagUtils {
    private static final String BUNDLE_BASENAME = "prop.pagecontent";
    private static final String LOCALE_SPLITTER = "_";
    private static final int INDEX_DIFFERENCE = 1;
    private static final int LANG_KEY = 0;
    private static final int COUNTRY_KEY = 1;

    private TagUtils() {
    }

    public static ResourceBundle findBundle(HttpSession session) {
        String locale = (String) session.getAttribute(LOCALE);
        String[] tags = locale.split(LOCALE_SPLITTER);
        Locale currentLocale = new Locale(tags[LANG_KEY], tags[COUNTRY_KEY]);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_BASENAME, currentLocale);
        return resourceBundle;
    }

    public static Map<String, List<String>> findAttributes(ServletRequest request) {
        Pagination pagination = (Pagination) request.getAttribute(PAGINATION);
        Map<String, List<String>> requestAttributes = pagination.getRequestAttributes();
        return requestAttributes;
    }
}
