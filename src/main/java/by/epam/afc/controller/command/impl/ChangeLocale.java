package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.epam.afc.controller.RequestAttribute.LOCALE;
import static by.epam.afc.controller.SessionAttribute.LANGUAGE;
import static by.epam.afc.controller.SessionAttribute.LATEST_CONTEXT_PATH;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class ChangeLocale implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String locale = request.getParameter(LOCALE);
        if (locale == null) {
            locale = "en_US";
        }
        switch (locale) {
            case "ru_RU":
                session.setAttribute(LOCALE, "ru_RU");
                session.setAttribute(LANGUAGE, "Русский(RU)");
                break;
            default:
                session.setAttribute(LOCALE, "en_US");
                session.setAttribute(LANGUAGE, "English(US)");
        }
        String latestPath = (String) session.getAttribute(LATEST_CONTEXT_PATH);
        return new Router(FORWARD, "/jsp/pages/aboutUser.jsp");
    }
}
