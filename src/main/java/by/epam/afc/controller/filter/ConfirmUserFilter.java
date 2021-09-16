package by.epam.afc.controller.filter;

import by.epam.afc.dao.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.epam.afc.controller.PagePath.ACCOUNT_CONFIRMATION;
import static by.epam.afc.controller.SessionAttribute.AUTHORIZED;
import static by.epam.afc.controller.SessionAttribute.USER;

@WebFilter(urlPatterns = {"/jsp/pages/*"})
public class ConfirmUserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        boolean authorized = (boolean) session.getAttribute(AUTHORIZED);
        if (authorized) {
            User user = (User) session.getAttribute(USER);
            User.Status status = user.getStatus();
            if (status == User.Status.DELAYED_REG) {
                String url = request.getContextPath() + ACCOUNT_CONFIRMATION;
                request.getRequestDispatcher(ACCOUNT_CONFIRMATION).forward(servletRequest, servletResponse);
                return;
            }
        }
        doFilter(servletRequest, servletResponse, filterChain);
    }
}
