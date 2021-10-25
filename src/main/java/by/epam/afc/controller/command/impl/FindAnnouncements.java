package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.controller.command.pagination.AnnouncementsPagination;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.AnnouncementFilterParser;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import by.epam.afc.service.validator.impl.AnnouncementFilterValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.epam.afc.controller.PagePath.*;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.RequestAttribute.LOAD_ONLY;
import static by.epam.afc.controller.SessionAttribute.PAGINATION_DATA;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;
import static by.epam.afc.dao.entity.Announcement.Status.ACTIVE;

public class FindAnnouncements implements Command {
    private static Logger logger = LogManager.getLogger(FindAnnouncements.class);

    private static final String ONLY_LOAD_ANNOUNCEMENTS = "1";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        AnnouncementsPagination pagination = (AnnouncementsPagination) session.getAttribute(PAGINATION_DATA);
        String loadOnly = request.getParameter(LOAD_ONLY);

        if(pagination != null && pagination.getClass() != AnnouncementsPagination.class){
            pagination = null;
        }

        if(loadOnly != null){
            if(loadOnly.equals(ONLY_LOAD_ANNOUNCEMENTS) && pagination != null){
                return new Router(FORWARD, ANNOUNCEMENTS_PAGE);
            }
        }

        try {
            AnnouncementServiceImpl announcementService = AnnouncementServiceImpl.getInstance();
            Optional<AnnouncementsPagination> optionalPagination;
            if (pagination != null) {
                Map<String, String[]> requestParameterMap = request.getParameterMap();
                AnnouncementFilterValidatorImpl filterValidator = AnnouncementFilterValidatorImpl.getInstance();
                boolean validMap = filterValidator.validateParameterMap(requestParameterMap);

                if (validMap) {
                    AnnouncementFilterParser filterParser = AnnouncementFilterParser.getInstance();
                    AnnouncementsPagination parsedFilter = filterParser.parseFilter(requestParameterMap);
                    if (!parsedFilter.equals(pagination)) {

                        System.out.println("New pagination filter");
                        parsedFilter.setCurrentPage(0);
                        optionalPagination = announcementService.findAnnouncements(parsedFilter);
                    } else {
                        System.out.println("The same pagination filter, no need update" + pagination);
                        return new Router(FORWARD, ANNOUNCEMENTS_PAGE);
                    }
                } else {
                    logger.warn("Request parameter map is invalid!");
                    session.setAttribute(PAGINATION_DATA, null);
                    return new Router(FORWARD, INDEX);
                }

            } else {
                System.out.println("PAGINATION NULL");
                AnnouncementsPagination emptyPagination = new AnnouncementsPagination(ACTIVE);
                optionalPagination = announcementService.findAnnouncements(emptyPagination);
            }

            if (!optionalPagination.isPresent()) {
                session.setAttribute(PAGINATION_DATA, null);
                request.setAttribute(EXCEPTION_MESSAGE, "Pagination data is not presented");
                return new Router(FORWARD, ERROR_500);
            } else {
                System.out.println("Setting up new DATA" + optionalPagination.get());
                session.setAttribute(PAGINATION_DATA, optionalPagination.get());
                return new Router(FORWARD, ANNOUNCEMENTS_PAGE);
            }

        } catch (ServiceException e) {
            logger.error("Error occurred while loading paginated data", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Error occurred while loading paginated data: " + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
