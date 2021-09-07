package by.epam.afc.controller.command.impl.go;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

import static by.epam.afc.controller.PagePath.REGISTER_PAGE;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;
import static by.epam.afc.controller.command.Router.DispatchType.REDIRECT;

public class ToRegisterPage implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(REDIRECT, request.getContextPath() + REGISTER_PAGE);
    }
}
