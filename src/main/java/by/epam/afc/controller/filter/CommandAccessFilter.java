package by.epam.afc.controller.filter;

import by.epam.afc.controller.command.CommandProvider;
import by.epam.afc.controller.command.CommandType;
import by.epam.afc.dao.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static by.epam.afc.controller.PagePath.CONTROLLER;
import static by.epam.afc.controller.RequestAttribute.COMMAND;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.CommandType.*;
import static by.epam.afc.dao.entity.User.Role.GUEST;
import static by.epam.afc.dao.entity.User.Status.BANNED;
import static by.epam.afc.dao.entity.User.Status.DELAYED_REG;

@WebFilter(urlPatterns = {"/controller"})
public class CommandAccessFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(CommandAccessFilter.class);

    private static List<CommandType> inactiveUserCommands;
    private static List<CommandType> bannedUserCommands;
    private static List<CommandType> guestCommands;
    private static List<CommandType> userCommands;
    private static List<CommandType> moderatorCommands;
    private static List<CommandType> administratorCommands;

    private static final String FORWARD_URL = CONTROLLER + "?" + COMMAND + "=";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        inactiveUserCommands = Arrays.asList(
                TO_CONFIRM_PAGE,
                CONFIRM_ACCOUNT,
                CHANGE_LOCALE,
                LOGOUT_COMMAND
        );
        bannedUserCommands = Arrays.asList(
                TO_BAN_PAGE,
                CHANGE_LOCALE,
                LOGOUT_COMMAND
        );
        guestCommands = Arrays.asList(
                FIND_ANNOUNCEMENTS,
                TO_LOGIN_PAGE,
                TO_REGISTER_PAGE,
                LOGIN_COMMAND,
                CHANGE_LOCALE,
                SHOW_ANNOUNCEMENT,
                REGISTER_COMMAND
        );
        userCommands = Arrays.asList(
                FIND_ANNOUNCEMENTS,
                TO_MY_ANNOUNCEMENTS_PAGE,
                FIND_MY_ANNOUNCEMENTS,
                TO_SUBMIT_AD_PAGE,
                SUBMIT_ANNOUNCEMENT,
                CHANGE_LOCALE,
                LOGOUT_COMMAND,
                SHOW_ANNOUNCEMENT,
                TO_MY_PROFILE,
                UPDATE_MY_PROFILE,
                CHANGE_ANNOUNCEMENT_STATUS,
                TO_EDIT_ANNOUNCEMENT,
                UPDATE_ANNOUNCEMENT
        );
        moderatorCommands = Arrays.asList(
                TO_MODERATOR_PAGE,
                CONFIRM_ANNOUNCEMENT,
                DEACTIVATE_ANNOUNCEMENT
        );
        administratorCommands = Arrays.asList(
                TO_ADMINISTRATOR_PAGE,
                FIND_USERS,
                BAN_USER,
                TO_EDIT_USER_MODAL,
                UPDATE_USER
        );
        moderatorCommands = new ArrayList<>(moderatorCommands);
        administratorCommands = new ArrayList<>(administratorCommands);
        moderatorCommands.addAll(userCommands);
        administratorCommands.addAll(moderatorCommands);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String commandName = request.getParameter(COMMAND);
        CommandProvider commandProvider = CommandProvider.getInstance();
        Optional<CommandType> command = commandProvider.typeForName(commandName);
        if (!command.isPresent() || !isPermitted(user, command.get())) {
            if (command.isPresent()) {
                logger.debug("Command: {} is not permitted for {}", commandName, user.getRole());
            }
            CommandType forwardCommand = defineForward(user);
            request.getRequestDispatcher(FORWARD_URL + forwardCommand).forward(servletRequest, servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private CommandType defineForward(User user) {
        CommandType command;
        if (user.getRole() != GUEST) {
            switch (user.getStatus()) {
                case DELAYED_REG:
                    command = TO_CONFIRM_PAGE;
                    break;
                case BANNED:
                    command = TO_BAN_PAGE;
                    break;
                default:
                    command = FIND_ANNOUNCEMENTS;
            }
        } else {
            command = TO_LOGIN_PAGE;
        }
        return command;
    }

    private boolean isPermitted(User user, CommandType command) {
        if (user.getRole() == GUEST) {
            return guestCommands.contains(command);
        }
        if (user.getStatus() == DELAYED_REG) {
            return inactiveUserCommands.contains(command);
        }
        if (user.getStatus() == BANNED) {
            return bannedUserCommands.contains(command);
        }
        boolean permitted;
        switch (user.getRole()) {
            case MODERATOR:
                permitted = moderatorCommands.contains(command);
                break;
            case ADMINISTRATOR:
                permitted = administratorCommands.contains(command);
                break;
            default:
                permitted = userCommands.contains(command);
        }
        return permitted;
    }
}
