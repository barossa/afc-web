package by.epam.afc.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.epam.afc.controller.PagePath.INDEX;
import static by.epam.afc.controller.SessionAttribute.AUTHORIZED;

public class AuthAccessFilter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        boolean isAuthorized = (boolean) session.getAttribute(AUTHORIZED);
        if(!isAuthorized){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            request.getRequestDispatcher(INDEX).forward(servletRequest, servletResponse);
        }
    }
}
