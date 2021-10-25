package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.controller.command.pagination.AnnouncementsPagination;
import by.epam.afc.controller.command.pagination.MyAnnouncementsPagination;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.PagePath.MY_ANNOUNCEMENTS_PAGE;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.RequestAttribute.STATUS;
import static by.epam.afc.controller.SessionAttribute.PAGINATION_DATA;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;
import static by.epam.afc.dao.entity.Announcement.Status.UNDEFINED;

public class FindMyAnnouncements implements Command {
    private static Logger logger = LogManager.getLogger(FindMyAnnouncements.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        AnnouncementsPagination pagination = (AnnouncementsPagination) session.getAttribute(PAGINATION_DATA);
        User user = (User) session.getAttribute(USER);

        if(pagination != null && pagination.getClass() != MyAnnouncementsPagination.class){
            pagination = null;
        }

        try {
            AnnouncementServiceImpl announcementService = AnnouncementServiceImpl.getInstance();
            Optional<AnnouncementsPagination> optionalPagination;
            if (pagination == null) {
                System.out.println("PAGINATION NULL");
                AnnouncementsPagination emptyPagination = new MyAnnouncementsPagination(UNDEFINED);
                optionalPagination = announcementService.findAnnouncements(emptyPagination, user);
            } else {
                String statusParam = request.getParameter(STATUS);
                if(statusParam != null){
                    Announcement.Status status = defineStatus(statusParam);
                    if(pagination.getStatus() != status){
                        pagination.setStatus(status);
                        pagination.setCurrentPage(0);
                    }
                }

                optionalPagination = announcementService.findAnnouncements(pagination, user);
            }

            if (!optionalPagination.isPresent()) {
                session.setAttribute(PAGINATION_DATA, null);
                request.setAttribute(EXCEPTION_MESSAGE, "Pagination data is not presented");
                return new Router(FORWARD, ERROR_500);
            } else {
                System.out.println("Setting up new DATA" + optionalPagination.get());
                session.setAttribute(PAGINATION_DATA, optionalPagination.get());
                return new Router(FORWARD, MY_ANNOUNCEMENTS_PAGE);
            }

        } catch (ServiceException e) {
            logger.error("Error occurred while loading paginated data", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Error occurred while loading paginated data: " + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }

    private Announcement.Status defineStatus(String status){
        Announcement.Status value = UNDEFINED;
        if(status != null){
            switch (status.toUpperCase()){
                case "MODERATING":
                case "ACTIVE":
                case "INACTIVE":
                    value = Announcement.Status.valueOf(status.toUpperCase());
            }
        }
        return value;
    }
}
