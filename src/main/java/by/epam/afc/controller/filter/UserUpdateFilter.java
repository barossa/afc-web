package by.epam.afc.controller.filter;

import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.UserService;
import by.epam.afc.service.impl.UserServiceImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static by.epam.afc.controller.ContextAttribute.USERS_TO_UPDATE;
import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.dao.entity.User.Role.GUEST;

/**
 * The type User update filter.
 */
@WebFilter(urlPatterns = {"/controller", "/index.jsp"})
public class UserUpdateFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(UserUpdateFilter.class);

    /**
     * Do filter.
     *
     * @param servletRequest  the servlet request
     * @param servletResponse the servlet response
     * @param filterChain     the filter chain
     * @throws IOException      Signals that an I/O exception has occurred
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user.getRole() != GUEST) {
            ServletContext servletContext = request.getServletContext();
            List<Integer> usersToUpdate = (List<Integer>) servletContext.getAttribute(USERS_TO_UPDATE);
            if (usersToUpdate.contains(user.getId())) {
                updateUser(request, servletResponse, user.getId());
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void updateUser(HttpServletRequest request, ServletResponse response, int userId) {
        try {
            UserService service = UserServiceImpl.getInstance();
            Optional<User> optionalUser = service.findById(userId);
            User user = optionalUser.orElseThrow(ServiceException::new);
            HttpSession session = request.getSession();
            session.setAttribute(USER, user);
        } catch (ServiceException e) {
            logger.error("Error occurred while updating user data:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Error occurred while updating user data: " + e.getMessage());
            try {
                request.getRequestDispatcher(ERROR_500).forward(request, response);
            } catch (ServletException | IOException ex) {
                logger.error("Can't forward to error page:", e);
            }
        }
    }

}
