package by.epam.afc.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.epam.afc.controller.PagePath.INDEX;
import static by.epam.afc.controller.SessionAttribute.LATEST_CONTEXT_PATH;

@WebFilter(urlPatterns = {"/*"})
public class LatestPageFilter implements Filter {
    private static final String REFERER = "referer";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("LATEST PAGE FILTER");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String currentUrl = request.getHeader(REFERER);
        if(currentUrl == null){
            currentUrl = request.getContextPath() + INDEX;
        }
        String contextPath = request.getContextPath();
        String currentPath = currentUrl.substring(contextPath.length());
        System.out.println("URL:" + currentUrl);
        System.out.println("LATEST CONTPATH: " + currentPath);
        session.setAttribute(LATEST_CONTEXT_PATH, currentPath);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
