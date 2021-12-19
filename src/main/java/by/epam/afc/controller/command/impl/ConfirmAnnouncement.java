package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.AnnouncementService;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import by.epam.afc.service.validator.NumberValidator;
import by.epam.afc.service.validator.impl.NumberValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.PagePath.MODERATOR_PAGE;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.RequestAttribute.ID;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

/**
 * The type Confirm announcement.
 */
public class ConfirmAnnouncement implements Command {
    private static final Logger logger = LogManager.getLogger(ConfirmAnnouncement.class);

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
            NumberValidator numberValidator = NumberValidatorImpl.getInstance();
            String idParam = request.getParameter(ID);
            boolean idValid = numberValidator.validateNumber(idParam);
            if (idValid) {
                int id = Integer.parseInt(idParam);
                AnnouncementService announcementService = AnnouncementServiceImpl.getInstance();
                announcementService.confirmAnnouncement(id);
            }
            return new Router(FORWARD, MODERATOR_PAGE);
        } catch (ServiceException e) {
            logger.error("Can't confirm announcement:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Can't confirm announcement:" + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
