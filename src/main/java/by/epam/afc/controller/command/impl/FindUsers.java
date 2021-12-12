package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Pagination;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.UserService;
import by.epam.afc.service.impl.UserServiceImpl;
import by.epam.afc.service.util.RequestParameterConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static by.epam.afc.controller.PagePath.ADMINISTRATOR_PAGE;
import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.RequestAttribute.PAGINATION;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class FindUsers implements Command {
    private static final Logger logger = LogManager.getLogger(FindUsers.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            RequestParameterConverter parameterConverter = RequestParameterConverter.getInstance();
            Map<String, List<String>> requestParameters = parameterConverter.transform(request.getParameterMap());
            UserService userService = UserServiceImpl.getInstance();
            Pagination<User> pagination = userService.findUsers(requestParameters);
            request.setAttribute(PAGINATION, pagination);
            return new Router(FORWARD, ADMINISTRATOR_PAGE);
        }catch (ServiceException e){
            logger.error("Can't display user's control page", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Can't display user's control page:" + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
