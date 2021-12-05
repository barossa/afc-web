package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.epam.afc.controller.PagePath.*;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class ShowAnnouncement implements Command {
    private static Logger logger = LogManager.getLogger(ShowAnnouncement.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String idParam = request.getParameter(ID);
            AnnouncementService announcementService = AnnouncementServiceImpl.getInstance();
            Optional<Announcement> announcement = announcementService.findById(idParam);
            if (announcement.isPresent()) {
                request.setAttribute(ANNOUNCEMENT, announcement.get());
                return new Router(FORWARD, ANNOUNCEMENT_PAGE);
            }
        } catch (ServiceException e) {
            logger.error("Can't show announcement page:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Can't show announcement page: " + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
        return new Router(FORWARD, INDEX);
    }
}
