package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.PagePath.LOGIN_PAGE;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;
import static by.epam.afc.controller.command.Router.DispatchType.REDIRECT;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router;
        String authField = request.getParameter(AUTH_FIELD);
        String password = request.getParameter(PASSWORD);
        try {
            UserServiceImpl service = UserServiceImpl.getInstance();
            Optional<User> optionalUser = service.authenticate(authField, password.toCharArray());
            if (optionalUser.isPresent()) {
                HttpSession session = request.getSession();
                User user = optionalUser.get();
                session.setAttribute(USER, user);
                router = new Router(REDIRECT, request.getContextPath());
            } else {
                router = new Router(FORWARD, LOGIN_PAGE);
            }
        } catch (ServiceException e) {
            request.setAttribute(EXCEPTION_MESSAGE, e.getMessage());
            router = new Router(FORWARD, ERROR_500);
        }
        return router;
    }
}
