package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import by.epam.afc.service.validator.AnnouncementValidator;
import by.epam.afc.service.validator.NumberValidator;
import by.epam.afc.service.validator.impl.AnnouncementValidatorImpl;
import by.epam.afc.service.validator.impl.NumberValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.PagePath.MY_ANNOUNCEMENTS_PAGE;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

/**
 * The type Change announcement status.
 */
public class ChangeAnnouncementStatus implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeAnnouncementStatus.class);

    /**
     * Execute router.
     *
     * @param request  the request
     * @param response the response
     * @return the router
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            AnnouncementValidator announcementValidator = AnnouncementValidatorImpl.getInstance();
            NumberValidator numberValidator = NumberValidatorImpl.getInstance();
            String statusParam = request.getParameter(STATUS);
            String idParam = request.getParameter(ID);
            boolean statusValid = announcementValidator.validateStatus(statusParam);
            boolean idValid = numberValidator.validateNumber(idParam);
            if (idValid && statusValid) {
                int id = Integer.parseInt(idParam);
                Announcement.Status status = Announcement.Status.valueOf(statusParam.toUpperCase());
                if (status != Announcement.Status.ACTIVE && status != Announcement.Status.UNDEFINED) {
                    Announcement announcement = Announcement.getBuilder()
                            .id(id)
                            .status(status)
                            .build();
                    HttpSession session = request.getSession();
                    User user = (User) session.getAttribute(USER);
                    AnnouncementService announcementService = AnnouncementServiceImpl.getInstance();
                    announcementService.changeAnnouncementsStatus(announcement, user);
                }
            }
            return new Router(FORWARD, MY_ANNOUNCEMENTS_PAGE);
        } catch (ServiceException e) {
            logger.error("Can't change announcement status:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Can't change announcement status" + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
