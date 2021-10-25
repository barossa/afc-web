package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.controller.command.pagination.AnnouncementsPagination;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.PagePath.INDEX;
import static by.epam.afc.controller.RequestAttribute.ACTION;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.SessionAttribute.PAGINATION_DATA;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;
import static by.epam.afc.controller.command.Router.DispatchType.REDIRECT;

public class ChangeAnnouncementsPage implements Command {
    private static Logger logger = LogManager.getLogger(ChangeAnnouncementsPage.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        AnnouncementsPagination pagination = (AnnouncementsPagination) session.getAttribute(PAGINATION_DATA);

        if (pagination != null) {
            String action = request.getParameter(ACTION);
            if(action == null){
                action = "";
            }
            switch (action) {
                case "next":
                    if (pagination.isNext()) {
                        int currentPage = pagination.getCurrentPage();
                        pagination.setCurrentPage(currentPage + 1);
                    }
                    break;
                case "previous":
                    if (pagination.isPrevious()) {
                        int currentPage = pagination.getCurrentPage();
                        pagination.setCurrentPage(currentPage - 1);
                    }
                    break;
                default:
                    return new Router(REDIRECT, request.getContextPath());
            }

            try {
                AnnouncementService announcementService = AnnouncementServiceImpl.getInstance();
                Optional<AnnouncementsPagination> foundedPaginationOptional = announcementService.findAnnouncements(pagination);
                AnnouncementsPagination foundedPagination = foundedPaginationOptional.orElseThrow(ServiceException::new);
                session.setAttribute(PAGINATION_DATA, foundedPagination);

            } catch (ServiceException e) {
                logger.error("Can't move to another paginated page", e);
                request.setAttribute(EXCEPTION_MESSAGE, "Can't move to another paginated page: " + e.getMessage());
                return new Router(FORWARD, ERROR_500);
            }

        }
        return new Router(REDIRECT, request.getContextPath());
    }
}
