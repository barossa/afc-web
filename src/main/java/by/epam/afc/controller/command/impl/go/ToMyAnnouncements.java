package by.epam.afc.controller.command.impl.go;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static by.epam.afc.controller.PagePath.MY_ANNOUNCEMENTS_PAGE;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class ToMyAnnouncements implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        return new Router(FORWARD, MY_ANNOUNCEMENTS_PAGE);
    }
}
