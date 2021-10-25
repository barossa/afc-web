package by.epam.afc.controller.filter;

import by.epam.afc.dao.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static by.epam.afc.controller.PagePath.*;
import static by.epam.afc.controller.SessionAttribute.USER;

@WebFilter(urlPatterns = {"/jsp/*"})
public class PageAccessFilter implements Filter {
    private static List<String> guestPages;
    private static List<String> userPages;
    private static List<String> moderatorPages;
    private static List<String> administratorPages;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        /*GUEST PAGES*/
        guestPages = Arrays.asList(
                INDEX,
                LOGIN_PAGE,
                REGISTER_PAGE

        );
        /*END OF GUEST PAGES*/

        /*USER PAGES*/
        userPages = Arrays.asList(
                INDEX,
                ABOUT_USER,
                BAN_PAGE,
                SUBMIT_AD_PAGE,
                FORGOT_PASS_PAGE
        );
        /*END OF USER PAGES*/

        /*MODERATOR PAGES*/
        moderatorPages = Arrays.asList(
                INDEX,
                ABOUT_USER,
                BAN_PAGE
        );
        /*END OF MODERATOR PAGES*/

        /*ADMINISTRATOR PAGES*/
        administratorPages = Arrays.asList(
                INDEX,
                ABOUT_USER,
                BAN_PAGE,
                SUBMIT_AD_PAGE,
                ANNOUNCEMENT_PAGE
        );
        /*END OF ADMINISTRATOR PAGES*/
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        System.out.println("SERVLET PATH: " + request.getServletPath());
        String servletPath = request.getServletPath();
        User user = (User) session.getAttribute(USER);
        User.Role role = user.getRole();

        boolean permitted = isPermitted(role, servletPath);
        if (permitted) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String url = request.getContextPath() + INDEX;
            response.sendRedirect(url);
        }
    }

    private boolean isPermitted(User.Role role, String servletPath) {
        boolean check;
        switch (role) {
            case GUEST:
                check = guestPages.contains(servletPath);
                break;
            case USER:
                check = userPages.contains(servletPath);
                break;
            case MODERATOR:
                check = moderatorPages.contains(servletPath);
                break;
            case ADMINISTRATOR:
                check = administratorPages.contains(servletPath);
                break;
            default:
                check = false;
        }
        return check;
    }

}
