package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.epam.afc.controller.PagePath.INDEX;
import static by.epam.afc.controller.RequestAttribute.LOCALE;
import static by.epam.afc.controller.SessionAttribute.LATEST_PATH;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

/**
 * The type Change locale.
 */
public class ChangeLocale implements Command {
    private static final String COMMAND_NAME = "CHANGE_LOCALE";
    private static final String REFERER_HEADER = "referer";
    private static final int NO_OCCURRENCES = -1;

    /**
     * Execute router.
     *
     * @param request  the request
     * @param response the response
     * @return the router
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String locale = request.getParameter(LOCALE);
        if (locale == null) {
            locale = "";
        }
        switch (locale) {
            case "ru_RU":
                session.setAttribute(LOCALE, "ru_RU");
                break;
            default:
                session.setAttribute(LOCALE, "en_US");
        }
        String lastPath = definePath(request);
        return new Router(FORWARD, lastPath);
    }

    private String definePath(HttpServletRequest request){
        String referer = request.getHeader(REFERER_HEADER);
        HttpSession session = request.getSession();
        String lastPath;
        if(referer != null && !containCommandName(referer)){
            String contextPath = request.getContextPath();
            lastPath = cutPath(referer, contextPath);
            session.setAttribute(LATEST_PATH, lastPath);
        }else{
            lastPath = (String) session.getAttribute(LATEST_PATH);
        }
        return (lastPath == null ? INDEX : lastPath);
    }

    private boolean containCommandName(String referer){
        String path = referer.toUpperCase();
        return path.contains(COMMAND_NAME);
    }

    private String cutPath(String referer, String contextPath){
        int index = referer.indexOf(contextPath);
        boolean inBounds = referer.length() > index + contextPath.length();
        if(index != NO_OCCURRENCES || inBounds){
            return referer.substring(index + contextPath.length());
        }else{
            return INDEX;
        }
    }
}
