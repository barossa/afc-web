package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.epam.afc.controller.PagePath.INDEX;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.REDIRECT;
import static by.epam.afc.dao.entity.User.Role.GUEST;

/**
 * The type Logout command.
 */
public class LogoutCommand implements Command {
    /**
     * Execute router.
     *
     * @param request  the request
     * @param response the response
     * @return the router
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User guest = User.getBuilder()
                .role(GUEST)
                .build();
        session.setAttribute(USER, guest);
        return new Router(REDIRECT, request.getContextPath() + INDEX);
    }
}
