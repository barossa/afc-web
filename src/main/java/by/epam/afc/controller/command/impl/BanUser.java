package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.UserService;
import by.epam.afc.service.impl.UserServiceImpl;
import by.epam.afc.service.validator.NumberValidator;
import by.epam.afc.service.validator.impl.NumberValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.epam.afc.controller.PagePath.*;
import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class BanUser implements Command {
    private static final Logger logger = LogManager.getLogger(BanUser.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            String idParam = request.getParameter(ID);
            String reason = request.getParameter(REASON);
            NumberValidator numberValidator = NumberValidatorImpl.getInstance();
            boolean valid = numberValidator.validateNumber(idParam);
            if(valid){
                UserService userService = UserServiceImpl.getInstance();
                int id = Integer.parseInt(idParam);
                userService.banUser(id, reason);
            }
            return new Router(FORWARD, ADMINISTRATOR_PAGE);
        }catch (ServiceException e){
            logger.error("Can't ban user:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Can't ba user:" + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
