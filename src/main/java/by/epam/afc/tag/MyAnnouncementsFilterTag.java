package by.epam.afc.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static by.epam.afc.controller.RequestAttribute.STATUS;

public class MyAnnouncementsFilterTag extends TagSupport {
    private static Logger logger = LogManager.getLogger(MyAnnouncementsFilterTag.class);

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter jspWriter = pageContext.getOut();
            jspWriter.write("<div class=\"rounded\" id=\"filters\">");
            Map<String, List<String>> requestAttributes = TagUtils.findAttributes(pageContext.getRequest());
            List<String> statuses = requestAttributes.get(STATUS);
            boolean active = statuses.stream().map(String::toUpperCase).anyMatch(s -> s.equals("ACTIVE"));
            boolean inactive = statuses.stream().map(String::toUpperCase).anyMatch(s -> s.equals("INACTIVE"));
            boolean moderating = statuses.stream().map(String::toUpperCase).anyMatch(s -> s.equals("MODERATING"));
            boolean all = statuses.isEmpty();
            jspWriter.write(buildFilter("f1", "all", all));
            jspWriter.write(buildFilter("f2", "active", active));
            jspWriter.write(buildFilter("f3", "inactive", inactive));
            jspWriter.write(buildFilter("f4", "moderating", moderating));
            jspWriter.write("</div>");
            return EVAL_PAGE;
        } catch (IOException e) {
            logger.error("Can't display my announcements filter panel:", e);
            throw new JspException("Can't display my announcements filter panel", e);
        }
    }

    private String buildFilter(String id, String tag, boolean checked) {
        StringBuilder builder = new StringBuilder("<div class=\"form-check\">" +
                "<input class=\"form-check-input  radio\" name=\"status\" type=\"radio\" value=\"");
        builder.append(tag)
                .append("\" id=\"")
                .append(id)
                .append("\"");
        builder.append(checked ? " checked>" : ">")
                .append("<label class=\"form-check-label\" for=\"");
        builder.append(id)
                .append("\">");
        ResourceBundle resourceBundle = TagUtils.findBundle(pageContext.getSession());
        String tagKey = "announcements." + tag;
        builder.append(resourceBundle.getString(tagKey))
                .append("</label>" +
                        "</div>");
        return builder.toString();
    }
}
