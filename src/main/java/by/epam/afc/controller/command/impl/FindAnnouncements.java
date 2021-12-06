package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Pagination;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import by.epam.afc.service.util.RequestParameterUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static by.epam.afc.controller.PagePath.ANNOUNCEMENTS_PAGE;
import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.RequestAttribute.PAGINATION;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class FindAnnouncements implements Command {
    private static Logger logger = LogManager.getLogger(FindAnnouncements.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            AnnouncementService announcementService = AnnouncementServiceImpl.getInstance();
            Map<String, List<String>> parameterMap = RequestParameterUtils.transform(request.getParameterMap());
            Pagination<Announcement> pagination = announcementService.findAnnouncements(parameterMap);
            request.setAttribute(PAGINATION, pagination);
            return new Router(FORWARD, ANNOUNCEMENTS_PAGE);
        } catch (ServiceException e) {
            logger.error("Error occurred while loading paginated data", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Error occurred while loading paginated data: " + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }

    }
}
