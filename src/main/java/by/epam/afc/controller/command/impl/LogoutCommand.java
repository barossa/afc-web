package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.epam.afc.controller.PagePath.INDEX;
import static by.epam.afc.controller.SessionAttribute.AUTHORIZED;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.REDIRECT;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(AUTHORIZED, false);
        session.setAttribute(USER, null);
        return new Router(REDIRECT, request.getContextPath() + INDEX);
    }
}
