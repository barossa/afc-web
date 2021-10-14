package by.epam.afc.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.epam.afc.controller.PagePath.CONTROLLER;
import static by.epam.afc.controller.PagePath.INDEX;
import static by.epam.afc.controller.SessionAttribute.LATEST_CONTEXT_PATH;

@WebFilter(urlPatterns = {"/jsp/*"})
public class LatestPageFilter implements Filter {
    private static final String REFERER = "referer";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String currentUrl = request.getHeader(REFERER);
        if (currentUrl == null) {
            currentUrl = request.getContextPath() + INDEX;
        }

        if (!currentUrl.contains(CONTROLLER)) {
            String contextPath = request.getContextPath();
            int cutIndex = currentUrl.lastIndexOf(contextPath) + contextPath.length();
            String currentPath = currentUrl.substring(cutIndex);
            HttpSession session = request.getSession();
            session.setAttribute(LATEST_CONTEXT_PATH, currentPath);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
