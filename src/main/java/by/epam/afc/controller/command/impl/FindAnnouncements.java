package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.*;
import by.epam.afc.controller.command.pagination.AnnouncementPagination;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.epam.afc.controller.PagePath.*;
import static by.epam.afc.controller.SessionAttribute.PAGINATION_DATA;
import static by.epam.afc.controller.command.Router.DispatchType.*;

public class FindAnnouncements implements Command {
    private static Logger logger = LogManager.getLogger(FindAnnouncements.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        HttpSession session = request.getSession();

        AnnouncementPagination pagination = (AnnouncementPagination) session.getAttribute(PAGINATION_DATA);
        AnnouncementServiceImpl announcementService = AnnouncementServiceImpl.getInstance();
        try {
            Optional<AnnouncementPagination> optionalPagination;
            if (pagination == null) {
                optionalPagination = announcementService.findAnnouncements(requestParameterMap);
            } else {
                optionalPagination = announcementService.findAnnouncements(requestParameterMap, pagination);
            }

            if(!optionalPagination.isPresent()){
                session.setAttribute(PAGINATION_DATA, null);
                return new Router(FORWARD, request.getContextPath() + INDEX);
            }

            AnnouncementPagination announcementPagination = optionalPagination.get();
            session.setAttribute(PAGINATION_DATA, announcementPagination);
            return new Router(FORWARD, ANNOUNCEMENTS_PAGE);

        } catch (ServiceException e) {
            logger.error("Error occurred while loading paginated data", e);
            return new Router(FORWARD, ERROR_500);
        }
    }
}
