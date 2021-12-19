package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Pagination;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import by.epam.afc.service.util.RequestParameterConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.PagePath.MY_ANNOUNCEMENTS_PAGE;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.RequestAttribute.PAGINATION;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

/**
 * The type Find my announcements.
 */
public class FindMyAnnouncements implements Command {
    private static final Logger logger = LogManager.getLogger(FindMyAnnouncements.class);

    /**
     * Execute router.
     *
     * @param request  the request
     * @param response the response
     * @return the router
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            AnnouncementService announcementService = AnnouncementServiceImpl.getInstance();
            RequestParameterConverter parameterConverter = RequestParameterConverter.getInstance();
            Map<String, List<String>> parameterMap = parameterConverter.transform(request.getParameterMap());
            Pagination<Announcement> pagination = announcementService.findAnnouncements(parameterMap, user);
            request.setAttribute(PAGINATION, pagination);
            return new Router(FORWARD, MY_ANNOUNCEMENTS_PAGE);

        } catch (ServiceException e) {
            logger.error("Error occurred while loading paginated data", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Error occurred while loading paginated data: " + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
