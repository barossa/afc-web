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

import static by.epam.afc.controller.PagePath.*;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.SessionAttribute.*;
import static by.epam.afc.controller.command.Router.DispatchType.*;

public class LoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Router router;
        String authField = request.getParameter(AUTH_FIELD);
        String password = request.getParameter(PASSWORD);
        try {
            UserServiceImpl service = new UserServiceImpl();
            Optional<User> optionalUser = service.authenticate(authField, password.toCharArray());
            if(optionalUser.isPresent()){
                HttpSession session = request.getSession();
                session.setAttribute(USER, optionalUser.get());
                session.setAttribute(AUTHORIZED, true);
                router = new Router(REDIRECT,request.getContextPath() +  ABOUT_USER);
            }else{
                request.setAttribute(WRONG_LOGIN_OR_PASSWORD, true);
                router = new Router(FORWARD, LOGIN_PAGE);
            }
        } catch (ServiceException e) {
            //// TODO: 9/4/21 CUSTOM ERROR PAGE
            request.setAttribute(EXCEPTION, e);
            router = new Router(REDIRECT, ERROR_505);
        }
        return router;
    }
}
