package by.epam.afc.tag;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static by.epam.afc.controller.RequestAttribute.SEARCH;

/**
 * The type Announcements search tag.
 */
public class AnnouncementsSearchTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger();

    private static final String SEARCH_PLACEHOLDER = "announcements.searchPlaceholder";
    private static final String SEARCH_BUTTON = "announcements.search";

    /**
     * Do start tag int.
     *
     * @return the int
     * @throws JspException the jsp exception
     */
    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter jspWriter = pageContext.getOut();
            jspWriter.write("<form id=\"searchForm\" onsubmit=\"return false;\">" +
                    "<div id=\"search\">" +
                    "<div class=\"input-group\">" +
                    "<input type=\"text\" id=\"searchRequest\" name=\"search\" class=\"form-control rounded\"" +
                    "placeholder=\"");
            ResourceBundle resourceBundle = TagUtils.findBundle(pageContext.getSession());
            String searchPlaceholder = resourceBundle.getString(SEARCH_PLACEHOLDER);
            jspWriter.write(searchPlaceholder);
            jspWriter.write("\"");
            ServletRequest request = pageContext.getRequest();
            Map<String, List<String>> requestAttributes = TagUtils.findAttributes(request);
            List<String> searches = requestAttributes.get(SEARCH);
            Optional<String> search = searches.stream().findFirst();
            if (search.isPresent()) {
                jspWriter.write(" value=\"" + search.get() + "\"");
            }
            jspWriter.write("aria-label=\"Search\"" +
                    "aria-describedby=\"search-addon\"/>" +
                    "<button type=\"button\" id=\"sb\" class=\"btn btn-primary\">");
            String searchButton = resourceBundle.getString(SEARCH_BUTTON);
            jspWriter.write(searchButton);
            jspWriter.write("</button>" +
                    "</div>" +
                    "</div>" +
                    "</form>");
        } catch (IOException e) {
            logger.error("Can't display announcement search panel:", e);
            throw new JspException("Can't display announcement search panel", e);
        }
        return EVAL_PAGE;
    }
}
