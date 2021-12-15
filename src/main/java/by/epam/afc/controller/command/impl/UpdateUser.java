package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.UserService;
import by.epam.afc.service.impl.UserServiceImpl;
import by.epam.afc.service.util.RequestParameterConverter;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.epam.afc.controller.ContextAttribute.USERS_TO_UPDATE;
import static by.epam.afc.controller.PagePath.ADMINISTRATOR_PAGE;
import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class UpdateUser implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateUser.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestParameterConverter parameterConverter = RequestParameterConverter.getInstance();
            Map<String, String> credentials = parameterConverter.findCredentials(request.getParameterMap());
            UserService userService = UserServiceImpl.getInstance();
            Optional<User> userOptional = userService.updateCredentials(credentials);
            if (userOptional.isPresent()) {
                User updatedUser = userOptional.get();
                ServletContext servletContext = request.getServletContext();
                List<Integer> usersToUpdate = (List<Integer>) servletContext.getAttribute(USERS_TO_UPDATE);
                usersToUpdate.add(updatedUser.getId());
                servletContext.setAttribute(USERS_TO_UPDATE, usersToUpdate);
            }
            return new Router(FORWARD, ADMINISTRATOR_PAGE);
        } catch (ServiceException e) {
            logger.error("Can't update user data:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Can't update user data: " + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
