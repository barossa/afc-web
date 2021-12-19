package by.epam.afc.controller.command.impl.go;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static by.epam.afc.controller.PagePath.BAN_PAGE;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

/**
 * The type To ban page.
 */
public class ToBanPage implements Command {
    /**
     * Execute router.
     *
     * @param request  the request
     * @param response the response
     * @return the router
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        return new Router(FORWARD, BAN_PAGE);
    }
}
