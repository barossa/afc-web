package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.RequestAttribute;
import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.UserServiceImpl;
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

import static by.epam.afc.controller.PagePath.*;
import static by.epam.afc.controller.RequestAttribute.COMMAND;
import static by.epam.afc.controller.SessionAttribute.AUTHORIZED;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.CommandType.TO_CONFIRM_PAGE;
import static by.epam.afc.controller.command.Router.DispatchType.NOT_REQUIRED;
import static by.epam.afc.controller.command.Router.DispatchType.REDIRECT;
import static by.epam.afc.service.validator.impl.CredentialsValidatorImpl.*;

public class RegisterCommand implements Command {
    private static final String JSON_CONTENT_TYPE = "application/json";
    public static final String REDIRECT_KEY = "redirect";
    private static final String CONFIRMATION_REDIRECT = CONTROLLER + COMMAND + "=" + TO_CONFIRM_PAGE;

    Logger logger = LogManager.getLogger(RegisterCommand.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String firstname = request.getParameter(RequestAttribute.FIRSTNAME);
        String lastname = request.getParameter(RequestAttribute.LASTNAME);
        String login = request.getParameter(RequestAttribute.LOGIN);
        String email = request.getParameter(RequestAttribute.EMAIL);
        String phone = request.getParameter(RequestAttribute.PHONE);
        String password = request.getParameter(RequestAttribute.PASSWORD);
        String passwordRepeat = request.getParameter(RequestAttribute.PASSWORD_REPEAT);

        Map<String, String> credentialsMap = new HashMap<>();
        credentialsMap.put(FIRSTNAME, firstname);
        credentialsMap.put(LASTNAME, lastname);
        credentialsMap.put(LOGIN, login);
        credentialsMap.put(EMAIL, email);
        credentialsMap.put(PHONE, phone);
        credentialsMap.put(PASSWORD, password);
        credentialsMap.put(PASSWORD_REPEAT, passwordRepeat);

        CredentialsValidatorImpl credentialsValidator = CredentialsValidatorImpl.getInstance();
        Map<String, String> validatedCredentials = credentialsValidator.validateCredentials(credentialsMap);
        if (!validatedCredentials.containsValue("")) {// Regex validation
            try {
                existCheck(validatedCredentials);
                if (!validatedCredentials.containsValue("")) {// Exists validation
                    UserServiceImpl userService = new UserServiceImpl();
                    Optional<User> registeredUser = userService.register(credentialsMap);
                    User user = registeredUser.orElseThrow(ServiceException::new);
                    HttpSession session = request.getSession();
                    session.setAttribute(USER, user);
                    session.setAttribute(AUTHORIZED, true);
                    logger.debug("Registered new User[id=" + user.getId() + "]");

                    /*return new Router(REDIRECT, request.getContextPath() + CONFIRMATION_REDIRECT);
*/
                    Map<String, String> redirectResponse = new HashMap<>();
                    redirectResponse.put(REDIRECT_KEY, request.getContextPath() + CONFIRMATION_REDIRECT);
                    sendJsonResponse(redirectResponse, response);
                }
            } catch (ServiceException e) {
                logger.error("Can't validate registration credential", e);
                return new Router(REDIRECT, request.getContextPath() + ERROR_505);
            } catch (IOException e){
                logger.error("Error occurred while sending JSON redirect.", e);
                return new Router(REDIRECT, request.getContextPath() + ERROR_505);
            }
        }

        try {
            sendJsonResponse(validatedCredentials, response);
        } catch (IOException e) {
            logger.error("Error occurred while sending JSON response.", e);
            return new Router(REDIRECT, ERROR_500);
        }

        return new Router(NOT_REQUIRED, NONE);
    }

    private void existCheck(Map<String, String> credentialsMap) throws ServiceException {
        UserServiceImpl userService = new UserServiceImpl();
        String login = credentialsMap.get(LOGIN);
        String email = credentialsMap.get(EMAIL);
        String phone = credentialsMap.get(PHONE);
        if (userService.findLogin(login)) {
            credentialsMap.put(LOGIN, "");
        }
        if (userService.findEmail(email)) {
            credentialsMap.put(EMAIL, "");
        }
        if (userService.findPhone(phone)) {
            credentialsMap.put(PHONE, "");
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
