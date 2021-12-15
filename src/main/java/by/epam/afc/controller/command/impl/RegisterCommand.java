package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.UserServiceImpl;
import by.epam.afc.service.util.RequestParameterConverter;
import by.epam.afc.service.validator.impl.CredentialsValidatorImpl;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.PagePath.REGISTER_PAGE;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;
import static by.epam.afc.controller.command.Router.DispatchType.REDIRECT;
import static by.epam.afc.service.validator.impl.CredentialsValidatorImpl.NOT_VALID;

public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegisterCommand.class);

    private static final String JSON_CONTENT_TYPE = "application/json";
    public static final String REDIRECT_KEY = "redirect";

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        RequestParameterConverter parameterConverter = RequestParameterConverter.getInstance();
        Map<String, String> credentials = parameterConverter.findCredentials(request.getParameterMap());
        CredentialsValidatorImpl credentialsValidator = CredentialsValidatorImpl.getInstance();
        Map<String, String> validatedCredentials = credentialsValidator.validateCredentials(credentials);
        if (!validatedCredentials.containsValue(NOT_VALID)) {// Regex validation
            try {
                existCheck(validatedCredentials);
                if (!validatedCredentials.containsValue(NOT_VALID)) {// Exists validation
                    UserServiceImpl userService = UserServiceImpl.getInstance();
                    Optional<User> registeredUser = userService.register(credentials);
                    User user = registeredUser.orElseThrow(ServiceException::new);
                    HttpSession session = request.getSession();
                    session.setAttribute(USER, user);
                    logger.debug("Registered new User[id=" + user.getId() + "]");
                    Map<String, String> redirectResponse = new HashMap<>();
                    redirectResponse.put(REDIRECT_KEY, request.getContextPath());
                    sendJsonResponse(redirectResponse, response);
                }
            } catch (ServiceException e) {
                logger.error("Can't validate registration credential", e);
                request.setAttribute(EXCEPTION_MESSAGE, "Can't validate registration credential: " + e.getMessage());
                return new Router(REDIRECT, request.getContextPath() + ERROR_500);
            } catch (IOException e) {
                logger.error("Error occurred while sending JSON redirect.", e);
                request.setAttribute(EXCEPTION_MESSAGE, "Error occurred while sending JSON redirect: " + e.getMessage());
                return new Router(REDIRECT, request.getContextPath() + ERROR_500);
            }
        }
        try {
            sendJsonResponse(validatedCredentials, response);
        } catch (IOException e) {
            logger.error("Error occurred while sending JSON response.", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Error occurred while sending JSON response: " + e.getMessage());
            return new Router(REDIRECT, ERROR_500);
        }
        return new Router(FORWARD, REGISTER_PAGE);
    }

    private void existCheck(Map<String, String> credentialsMap) throws ServiceException {
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String login = credentialsMap.get(LOGIN);
        String email = credentialsMap.get(EMAIL);
        String phone = credentialsMap.get(PHONE);
        if (userService.findLogin(login)) {
            credentialsMap.put(LOGIN, NOT_VALID);
        }
        if (userService.findEmail(email)) {
            credentialsMap.put(EMAIL, NOT_VALID);
        }
        if (userService.findPhone(phone)) {
            credentialsMap.put(PHONE, NOT_VALID);
        }
    }

    private void sendJsonResponse(Map<String, String> data, HttpServletResponse response) throws IOException {
        String jsonResponse = new Gson().toJson(data);
        response.setContentType(JSON_CONTENT_TYPE);
        try (PrintWriter writer = response.getWriter()) {
            writer.append(jsonResponse);
        }
    }

}
