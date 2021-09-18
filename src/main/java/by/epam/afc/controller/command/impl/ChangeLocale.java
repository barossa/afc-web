package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.epam.afc.controller.PagePath.INDEX;
import static by.epam.afc.controller.RequestAttribute.LOCALE;
import static by.epam.afc.controller.SessionAttribute.LANGUAGE;
import static by.epam.afc.controller.SessionAttribute.LATEST_CONTEXT_PATH;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class ChangeLocale implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String locale = request.getParameter(LOCALE);
        if (locale == null) {
            locale = "en_US";
        }
        switch (locale) {
            case "ru_RU":
                session.setAttribute(LOCALE, "ru_RU");
                break;
            default:
                session.setAttribute(LOCALE, "en_US");
        }
        String latestPath = (String) session.getAttribute(LATEST_CONTEXT_PATH);
        if(latestPath == null){
            latestPath = INDEX;
        }
        return new Router(FORWARD, latestPath);
    }
}
