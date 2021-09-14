package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

import static by.epam.afc.controller.PagePath.INDEX;

public class RegisterCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(Router.DispatchType.REDIRECT, request.getContextPath() + INDEX);
    }
}
