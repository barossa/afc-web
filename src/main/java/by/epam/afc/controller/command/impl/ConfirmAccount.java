package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.UserService;
import by.epam.afc.service.impl.UserServiceImpl;
import by.epam.afc.service.validator.NumberValidator;
import by.epam.afc.service.validator.impl.NumberValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.RequestAttribute.CODE;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.SessionAttribute.*;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;
import static by.epam.afc.controller.command.Router.DispatchType.REDIRECT;

public class ConfirmAccount implements Command {
    private static final Logger logger = LogManager.getLogger(ConfirmAccount.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String codeParameter = request.getParameter(CODE);
            NumberValidator validator = NumberValidatorImpl.getInstance();
            boolean valid = validator.validateNumber(codeParameter);
            if (valid) {
                Integer enteredCode = Integer.parseInt(codeParameter);
                HttpSession session = request.getSession();
                Integer correctCode = (Integer) session.getAttribute(VERIFICATION_CODE);
                if (correctCode.equals(enteredCode)) {
                    User user = (User) session.getAttribute(USER);
                    UserService userService = UserServiceImpl.getInstance();
                    Optional<User> userOptional = userService.activate(user);
                    User activatedUser = userOptional.orElseThrow(ServiceException::new);
                    session.setAttribute(USER, activatedUser);
                    session.setAttribute(VERIFICATION_CODE, null);
                    session.setAttribute(SENT_TIME, null);
                }
            }
        }catch (ServiceException e){
            logger.error("Error occurred while activating user account:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Error occurred while activating user account:" + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
        return new Router(REDIRECT, request.getContextPath());
    }
}
