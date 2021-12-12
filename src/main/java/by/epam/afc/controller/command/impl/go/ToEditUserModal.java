package by.epam.afc.controller.command.impl.go;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.epam.afc.controller.PagePath.EDIT_USER_MODAL;
import static by.epam.afc.controller.PagePath.ERROR_500;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.RequestAttribute.ID;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class ToEditUserModal implements Command {
    private static final Logger logger = LogManager.getLogger(ToEditUserModal.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try{
            String idParam = request.getParameter(ID);
            NumberValidator numberValidator = NumberValidatorImpl.getInstance();
            boolean valid = numberValidator.validateNumber(idParam);
            if(valid){
                UserService userService = UserServiceImpl.getInstance();
                int id = Integer.parseInt(idParam);
                Optional<User> userOptional = userService.findById(id);
                User empty = User.getBuilder().build();
                request.setAttribute(USER, userOptional.orElse(empty));
            }
            return new Router(FORWARD, EDIT_USER_MODAL);
        }catch (ServiceException e){
            logger.error("Can't load edit modal:", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Can't load edit modal: " + e.getMessage());
            return new Router(FORWARD, ERROR_500);
        }
    }
}
