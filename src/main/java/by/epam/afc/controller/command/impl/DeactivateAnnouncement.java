package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import by.epam.afc.service.validator.AnnouncementValidator;
import by.epam.afc.service.validator.NumberValidator;
import by.epam.afc.service.validator.impl.AnnouncementValidatorImpl;
import by.epam.afc.service.validator.impl.NumberValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.PagePath.MODERATOR_PAGE;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class DeactivateAnnouncement implements Command {
    private static final Logger logger = LogManager.getLogger(DeactivateAnnouncement.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            AnnouncementValidator announcementValidator = AnnouncementValidatorImpl.getInstance();
            NumberValidator numberValidator = NumberValidatorImpl.getInstance();
            String reason = request.getParameter(REASON);
            String idParam = request.getParameter(ID);
            boolean reasonValid = announcementValidator.validateDescription(reason);
            boolean idValid = numberValidator.validateNumber(idParam);
            if (idValid && reasonValid) {
                int id = Integer.parseInt(idParam);
                AnnouncementService announcementService = AnnouncementServiceImpl.getInstance();
                announcementService.deactivateAnnouncement(id, reason);
            }
            return new Router(FORWARD, MODERATOR_PAGE);
        } catch (ServiceException e) {
            logger.error("Can't deactivate announcement:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Can't deactivate announcement:" + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
