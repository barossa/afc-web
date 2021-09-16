package by.epam.afc.controller.command.impl.go;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static by.epam.afc.controller.PagePath.FORGOT_PASS_PAGE;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class ToForgotPassPage implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        return new Router(FORWARD, request.getContextPath() + FORGOT_PASS_PAGE);
    }
}
