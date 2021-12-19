package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.UserService;
import by.epam.afc.service.impl.UserServiceImpl;
import by.epam.afc.service.util.RequestParameterConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.PagePath.MY_PROFILE;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.RequestAttribute.ID;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

/**
 * The type Update my profile.
 */
public class UpdateMyProfile implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateMyProfile.class);

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
            RequestParameterConverter parameterConverter = RequestParameterConverter.getInstance();
            Map<String, String> credentials = parameterConverter.findCredentials(request.getParameterMap());
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            credentials.put(ID, String.valueOf(user.getId()));
            UserService userService = UserServiceImpl.getInstance();
            Optional<User> userOptional = userService.updateMyCredentials(credentials);
            if (userOptional.isPresent()) {
                session.setAttribute(USER, userOptional.get());
            }
            return new Router(FORWARD, MY_PROFILE);
        } catch (ServiceException e) {
            logger.error("Error occurred updating my credentials:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Error occurred updating my credentials: " + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
