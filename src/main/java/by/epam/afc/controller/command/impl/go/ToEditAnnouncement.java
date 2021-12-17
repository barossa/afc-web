package by.epam.afc.controller.command.impl.go;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.controller.command.impl.ShowAnnouncement;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.epam.afc.controller.PagePath.*;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class ToEditAnnouncement implements Command {
    private static final Logger logger = LogManager.getLogger(ShowAnnouncement.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String idParam = request.getParameter(ID);
            AnnouncementService announcementService = AnnouncementServiceImpl.getInstance();
            Optional<Announcement> announcementOptional = announcementService.findById(idParam);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            if (announcementOptional.isPresent()) {
                Announcement announcement = announcementOptional.get();
                if (announcement.getOwner().getId() == user.getId()) {
                    request.setAttribute(ANNOUNCEMENT, announcement);
                    return new Router(FORWARD, EDIT_ANNOUNCEMENT_PAGE);
                }
            }
            return new Router(FORWARD, INDEX);
        } catch (ServiceException e) {
            logger.error("Can't edit announcement:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Can't edit announcement: " + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
