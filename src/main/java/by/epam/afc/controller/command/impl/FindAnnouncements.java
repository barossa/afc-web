package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Pagination;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import by.epam.afc.service.util.RequestParameterConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static by.epam.afc.controller.PagePath.ANNOUNCEMENTS_PAGE;
import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class FindAnnouncements implements Command {
    private static final Logger logger = LogManager.getLogger(FindAnnouncements.class);
    private static final String ACTIVE_STATUS = "active";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            AnnouncementService announcementService = AnnouncementServiceImpl.getInstance();
            RequestParameterConverter parameterConverter = RequestParameterConverter.getInstance();
            Map<String, List<String>> parameterMap = parameterConverter.transform(request.getParameterMap());
            List<String> status = new ArrayList<>();
            status.add(ACTIVE_STATUS);
            parameterMap.put(STATUS, status);
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
