package by.epam.afc.controller.command.impl.go;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static by.epam.afc.controller.PagePath.SUBMIT_AD_PAGE;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

/**
 * The type To submit ad page.
 */
public class ToSubmitAdPage implements Command {
    /**
     * Execute router.
     *
     * @param request  the request
     * @param response the response
     * @return the router
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        return new Router(FORWARD, SUBMIT_AD_PAGE);
    }
}
