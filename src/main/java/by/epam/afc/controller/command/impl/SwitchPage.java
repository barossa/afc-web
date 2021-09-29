package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static by.epam.afc.controller.PagePath.ANNOUNCEMENTS_PAGE;
import static by.epam.afc.controller.RequestAttribute.SWITCH_PARAMETER;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class SwitchPage implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        Object parameter = request.getAttribute(SWITCH_PARAMETER);
        if(parameter != null){


        }

        return new Router(FORWARD, ANNOUNCEMENTS_PAGE);
    }
}
