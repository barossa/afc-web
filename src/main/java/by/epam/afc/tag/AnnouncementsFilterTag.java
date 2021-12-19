package by.epam.afc.tag;

import by.epam.afc.dao.entity.Category;
import by.epam.afc.dao.entity.Region;
import jakarta.servlet.ServletContext;
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
import java.util.stream.Collectors;

import static by.epam.afc.controller.ContextAttribute.CATEGORIES;
import static by.epam.afc.controller.ContextAttribute.REGIONS;
import static by.epam.afc.controller.RequestAttribute.*;

/**
 * The type Announcements filter tag.
 */
public class AnnouncementsFilterTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(AnnouncementTableTag.class);

    private static final String PRICE_RANGE_LABEL = "filter.priceRange";
    private static final String RANGE_MIN_LABEL = "filter.rangeMin";
    private static final String RANGE_MAX_LABEL = "filter.rangeMax";
    private static final String CATEGORIES_LABEL = "filter.categories";
    private static final String REGIONS_LABEL = "filter.region";
    private static final String RESET_BUTTON = "filter.reset";

    /**
     * Do start tag int.
     *
     * @return the int
     * @throws JspException the jsp exception
     */
    @Override
    public int doStartTag() throws JspException {
        try {
            Map<String, List<String>> requestAttributes = TagUtils.findAttributes(pageContext.getRequest());
            List<String> rangesMin = requestAttributes.get(PRICE_MIN);
            List<String> rangesMax = requestAttributes.get(PRICE_MAX);
            Optional<String> rangeMin = rangesMin.stream().findFirst();
            Optional<String> rangeMax = rangesMax.stream().findFirst();
            JspWriter jspWriter = pageContext.getOut();
            ResourceBundle resourceBundle = TagUtils.findBundle(pageContext.getSession());
            jspWriter.write("<form id=\"filtersForm\" onsubmit=\"return false;\">" +
                    "<input type=\"hidden\" name=\"command\" value=\"find_announcements\"/>" +
                    "<article class=\"card-group-item\">" +
                    "<header class=\"card-header\">" +
                    "<h6 class=\"title\">");
            jspWriter.write(resourceBundle.getString(PRICE_RANGE_LABEL));
            jspWriter.write("</h6>" +
                    "</header>" +
                    "<div class=\"filter-content\">" +
                    "<div class=\"card-body\">" +
                    "<div class=\"form-row flex-c\" style=\"justify-content: space-around;\">" +
                    "<div class=\"form-group col-md-5\">" +
                    "<label>");
            jspWriter.write(resourceBundle.getString(RANGE_MIN_LABEL));
            jspWriter.write("</label>" +
                    "<input type=\"number\" id=\"rangeMin\" name=\"rangeMin\" class=\"form-control\"");
            if (rangeMin.isPresent()) {
                jspWriter.write("value=\"");
                jspWriter.write(rangeMin.get());
                jspWriter.write("\"");
            }
            jspWriter.write("pattern=\"[0-9]*\" min=\"0\"" +
                    "placeholder=\"BYN 0\">" +
                    "</div>" +
                    "<div class=\"form-group col-md-5 text-right\">" +
                    "<label>");
            jspWriter.write(resourceBundle.getString(RANGE_MAX_LABEL));
            jspWriter.write("</label>" +
                    "<input type=\"number\" id=\"rangeMax\" name=\"rangeMax\" class=\"form-control\"");
            if (rangeMax.isPresent()) {
                jspWriter.write("value=\"");
                jspWriter.write(rangeMax.get());
                jspWriter.write("\"");
            }
            jspWriter.write("pattern=\"[0-9]*\" min=\"0\"" +
                    "placeholder=\"BYN 100\">" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</article>" +
                    "<article class=\"card-group-item\">" +
                    "<header class=\"card-header\">" +
                    "<h6 class=\"title\">");
            jspWriter.write(resourceBundle.getString(CATEGORIES_LABEL));
            jspWriter.write("</h6>" +
                    "</header>" +
                    "<div class=\"filter-content flex-c\">" +
                    "<div class=\"card-body left-auto\">");
            ServletContext servletContext = pageContext.getServletContext();
            List<Category> categories = (List<Category>) servletContext.getAttribute(CATEGORIES);
            List<String> allCategories = categories.stream()
                    .map(Category::getId)
                    .map(String::valueOf)
                    .collect(Collectors.toList());
            List<String> requestCategories = requestAttributes.get(CATEGORY);
            for (String categoryId : allCategories) {
                boolean checked = requestCategories.contains(categoryId);
                String label = resourceBundle.getString("filter.category_" + categoryId);
                jspWriter.write(buildCheckbox("category", categoryId, label, checked));
            }
            jspWriter.write("</div>" +
                    "</div>" +
                    "</article>" +
                    "<article class=\"card-group-item\">" +
                    "<header class=\"card-header\">" +
                    "<h6 class=\"title\">");
            jspWriter.write(resourceBundle.getString(REGIONS_LABEL));
            jspWriter.write("</h6>" +
                    "</header>" +
                    "<div class=\"filter-content flex-c\">" +
                    "<div class=\"card-body left-auto\">");
            List<Region> regions = (List<Region>) servletContext.getAttribute(REGIONS);
            List<String> allRegions = regions.stream()
                    .map(Region::getId)
                    .map(String::valueOf)
                    .collect(Collectors.toList());
            List<String> requestRegions = requestAttributes.get(REGION);
            for (String regionId : allRegions) {
                boolean checked = requestRegions.contains(regionId);
                String label = resourceBundle.getString("filter.region_" + regionId);
                jspWriter.write(buildCheckbox("region", regionId, label, checked));
            }
            jspWriter.write("</div>" +
                    "</div>" +
                    "</article>" +
                    "<article class=\"flex-c justify-content-center m-b-15\">" +
                    "<button id=\"resetButton\" type=\"button\" class=\"btn btn-outline-secondary\">");
            jspWriter.write(resourceBundle.getString(RESET_BUTTON));
            jspWriter.write("</button>" +
                    "</article>" +
                    "</form>");
        } catch (IOException e) {
            logger.error("Can't display announcements filter panel: ", e);
            throw new JspException("Can't display announcements filter panel", e);
        }
        return EVAL_PAGE;
    }

    private String buildCheckbox(String name, String id, String label, boolean checked) {
        StringBuilder builder = new StringBuilder("<div class=\"custom-control custom-checkbox\">" +
                "<input type=\"checkbox\" name=\"");
        builder.append(name);
        builder.append("\" class=\"custom-control-input\"");
        builder.append(checked ? "checked=\"true\"" : " ");
        builder.append("value=\"");
        builder.append(id);
        builder.append("\">" +
                "  <label class=\"custom-control-label\">");
        builder.append(label);
        builder.append("</label>" +
                "</div>");
        return builder.toString();
    }
}
