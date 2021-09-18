package by.epam.afc.controller.filter;

import by.epam.afc.dao.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.epam.afc.controller.PagePath.BAN_PAGE;
import static by.epam.afc.controller.PagePath.CONFIRMATION_PAGE;
import static by.epam.afc.controller.SessionAttribute.AUTHORIZED;
import static by.epam.afc.controller.SessionAttribute.USER;

@WebFilter(urlPatterns = {"/*"})
public class UserStatusFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();

        boolean authorized = (boolean) session.getAttribute(AUTHORIZED);
        if (authorized) {
            User user = (User) session.getAttribute(USER);

            switch (user.getStatus()) {
                case DELAYED_REG:
                    request.getRequestDispatcher(CONFIRMATION_PAGE).forward(servletRequest, servletResponse);
                    return;
                case BANNED:
                    request.getRequestDispatcher(BAN_PAGE).forward(servletRequest, servletResponse);
                    return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
