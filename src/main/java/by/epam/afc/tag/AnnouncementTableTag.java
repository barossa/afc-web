package by.epam.afc.tag;

import by.epam.afc.controller.command.Pagination;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.Category;
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
import java.util.ResourceBundle;

import static by.epam.afc.controller.RequestAttribute.PAGINATION;
import static by.epam.afc.controller.SessionAttribute.USER;

public class AnnouncementTableTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(AnnouncementTableTag.class);

    private static final String NOTHING_FOUND = "search.nothingFound";
    private static final String FREE_ANNOUNCEMENT = "announcements.free";
    private static final String BASE64_PREFIX = "data:image/png;Base64,";
    private static final String MY_ANNOUNCEMENT = "announcements.myAnnouncement";

    @Override
    public int doStartTag() throws JspException {
        try {
            ServletRequest request = pageContext.getRequest();
            ResourceBundle resourceBundle = TagUtils.findBundle(pageContext.getSession());
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
        HttpSession session = pageContext.getSession();
        ResourceBundle resourceBundle = TagUtils.findBundle(session);
        User user = (User) session.getAttribute(USER);
        User owner = announcement.getOwner();
        String imgSource = BASE64_PREFIX + announcement.getPrimaryImage();
        float price = announcement.getPrice().floatValue();
        boolean myAnnouncement = user.getRole() != User.Role.GUEST && user.getId() == owner.getId();
        String priceTag = (price > 0F ? price + "BYN" : resourceBundle.getString(FREE_ANNOUNCEMENT));
        String publicationDate = announcement.getPublicationDate().format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
        Category category = announcement.getCategory();
        String categoryTag = resourceBundle.getString("filter.category_" + category.getId());
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
                .append(categoryTag)
                .append("</h6>")
                .append(announcement.getShortDescription())
                .append("</div>")
                .append("<p class=\"card-text\">")
                .append("<small class=\"text-muted\">")
                .append(buildNameTagElement(myAnnouncement, announcement.getOwner().getLogin()))
                .append("<i><i class=\"fa fa-calendar ms-2\"></i>")
                .append(" ")
                .append("<label>")
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

    private String buildNameTagElement(boolean mine, String owner) {
        ResourceBundle resourceBundle = TagUtils.findBundle(pageContext.getSession());
        String nameTag = resourceBundle.getString(MY_ANNOUNCEMENT);
        String element;
        if (mine) {
            element = "<a><label style=\"font-weight: bold\">" +
                    nameTag +
                    "</label></a>";
        } else {
            element = "<i>" +
                    "<i class=\"fa fa-user\"></i>" +
                    " " +
                    "<label style=\"font-style:normal\">" + owner + "</label>" +
                    "</i>";
        }
        return element;
    }
}
