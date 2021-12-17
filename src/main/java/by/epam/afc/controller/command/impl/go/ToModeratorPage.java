package by.epam.afc.controller.command.impl.go;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Pagination;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.PagePath.MODERATOR_PAGE;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;
import static by.epam.afc.dao.entity.Announcement.Status.MODERATING;

public class ToModeratorPage implements Command {
    private static final Logger logger = LogManager.getLogger(ToModeratorPage.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            AnnouncementService announcementService = AnnouncementServiceImpl.getInstance();
            String page = request.getParameter(PAGE);
            Map<String, List<String>> parameterMap = new HashMap<>();
            List<String> statuses = new ArrayList<>();
            List<String> pages = new ArrayList<>();
            statuses.add(MODERATING.toString());
            pages.add(page);
            parameterMap.put(STATUS, statuses);
            parameterMap.put(PAGE, pages);
            Pagination<Announcement> pagination = announcementService.findAnnouncements(parameterMap);
            request.setAttribute(PAGINATION, pagination);
            return new Router(FORWARD, MODERATOR_PAGE);
        } catch (ServiceException e) {
            logger.error("Can't display moderating panel:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Can't display moderating panel:" + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
