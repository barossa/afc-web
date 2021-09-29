package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.epam.afc.controller.PagePath.ANNOUNCEMENTS_PAGE;
import static by.epam.afc.controller.PagePath.ERROR_505;
import static by.epam.afc.controller.SessionAttribute.ANNOUNCEMENTS;
import static by.epam.afc.controller.SessionAttribute.CURRENT_PAGE;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;
import static by.epam.afc.controller.command.Router.DispatchType.REDIRECT;
import static by.epam.afc.dao.entity.Announcement.Status.ACTIVE;

public class FindAnnouncements implements Command {
    private static Logger logger = LogManager.getLogger(FindAnnouncements.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        AnnouncementServiceImpl announcementService = AnnouncementServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Integer currentPage = (Integer) session.getAttribute(CURRENT_PAGE);

        try {
            if (currentPage == null) {
                currentPage = 0;
                List<Announcement> announcements = announcementService.findAnnouncementsPage(currentPage, ACTIVE);
                session.setAttribute(CURRENT_PAGE, currentPage);
                session.setAttribute(ANNOUNCEMENTS, announcements);
            } else {
                currentPage = currentPage + 1;
                List<Announcement> announcements = announcementService.findAnnouncementsPage(currentPage, ACTIVE);
                session.setAttribute(CURRENT_PAGE, currentPage);
                session.setAttribute(ANNOUNCEMENTS, announcements);
            }
            return new Router(FORWARD, ANNOUNCEMENTS_PAGE);

        } catch (ServiceException e) {
            logger.error("Error occurred while loading paginated data", e);
            return new Router(REDIRECT, ERROR_505);
        }
    }
}
