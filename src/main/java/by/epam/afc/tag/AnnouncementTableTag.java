package by.epam.afc.tag;

import by.epam.afc.controller.command.Pagination;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.User;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static by.epam.afc.controller.RequestAttribute.LOCALE;
import static by.epam.afc.controller.RequestAttribute.PAGINATION;
import static by.epam.afc.controller.SessionAttribute.USER;

public class AnnouncementTableTag extends TagSupport {
    private static Logger logger = LogManager.getLogger(AnnouncementTableTag.class);

    private static final String BUNDLE_BASENAME = "prop.pagecontent";
    private static final String NOTHING_FOUND = "search.nothingFound";
    private static final String FREE_ANNOUNCEMENT = "announcements.free";
    private static final String BASE64_PREFIX = "data:image/png;Base64,";
    private static final String MY_ANNOUNCEMENT = "announcements.myAnnouncement";

    @Override
    public int doStartTag() throws JspException {
        try {
            ServletRequest request = pageContext.getRequest();
            ResourceBundle resourceBundle = findCurrentBundle();
            Pagination<Announcement> pagination = (Pagination<Announcement>) request.getAttribute(PAGINATION);
            List<Announcement> announcements = pagination.getData();
            JspWriter jspWriter = pageContext.getOut();

            if (announcements.isEmpty()) {
                jspWriter.write("<div class=\"card\">" +
                        resourceBundle.getString(NOTHING_FOUND) +
                        "</div>");
            }
            for (Announcement announcement : announcements) {
                String row = buildAnnouncementRow(announcement);
                jspWriter.write(row);
            }
        } catch (IOException e) {
            logger.error("Can't display announcements table:", e);
            throw new JspException("Can't display announcements table", e);
        }
        return EVAL_PAGE;
    }

    private String buildAnnouncementRow(Announcement announcement) {
        ResourceBundle resourceBundle = findCurrentBundle();
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute(USER);
        User owner = announcement.getOwner();
        String loginTag;
        if (user.getRole() != User.Role.GUEST && user.getId() == owner.getId()) {
            loginTag = resourceBundle.getString(MY_ANNOUNCEMENT);
        } else {
            loginTag = owner.getLogin();
        }
        String imgSource = BASE64_PREFIX + announcement.getPrimaryImage();
        float price = announcement.getPrice().floatValue();
        String priceTag = (price > 0F ? price + "BYN" : resourceBundle.getString(FREE_ANNOUNCEMENT));
        String publicationDate = announcement.getPublicationDate().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));

        StringBuilder builder = new StringBuilder()
                    .append("<div id=\"")
                .append(announcement.getId())
                    .append("\" class=\"card adCard\">")
                    .append("<div class=\"row\">")
                    .append("<div class=\"adCard-img\">")
                    .append("<img src=\"")
                .append(imgSource)
                    .append("\" width=\"130\" alt=\"No image\"/>")
                    .append("</div>")
                    .append("<div class=\"col-md-7\">")
                    .append("<div class=\"card-body\">")
                    .append("<h5 class=\"card-title\">")
                .append(announcement.getTitle())
                    .append("</h5>")
                    .append("<h6 class=\"card-subtitle\">")
                .append(announcement.getCategory().getDescription())
                    .append("</h6>")
                .append(announcement.getShortDescription())
                    .append("</div>")
                    .append("<p class=\"card-text\">")
                    .append("<small class=\"text-muted\">")
                    .append("<i class=\"fa fa-user\">")
                    .append("<label> ")
                .append(loginTag)
                    .append("</label>")
                    .append("</i>")
                    .append("<i class=\"fa fa-calendar ms-2\">")
                    .append("<label> ")
                .append(publicationDate)
                    .append("</label>")
                    .append("</i>")
                    .append("</small>")
                    .append("</p>")
                    .append("</div>")
                    .append("<div class=\"col-md-3\">")
                    .append("<div class=\"card-price\">")
                    .append("<h3>")
                .append(priceTag)
                    .append("</h3>")
                    .append("</div>")
                    .append("</div>")
                    .append("</div>")
                    .append("</div>");
        return builder.toString();
    }

    private ResourceBundle findCurrentBundle() {
        HttpSession session = pageContext.getSession();
        String locale = (String) session.getAttribute(LOCALE);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_BASENAME, Locale.forLanguageTag(locale));
        return resourceBundle;
    }
}
