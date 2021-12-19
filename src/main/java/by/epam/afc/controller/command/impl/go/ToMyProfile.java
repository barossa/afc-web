package by.epam.afc.controller.command.impl.go;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static by.epam.afc.controller.PagePath.MY_PROFILE;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

/**
 * The type To my profile.
 */
public class ToMyProfile implements Command {
    /**
     * Execute router.
     *
     * @param request  the request
     * @param response the response
     * @return the router
     */
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        return new Router(FORWARD, MY_PROFILE);
    }
}
