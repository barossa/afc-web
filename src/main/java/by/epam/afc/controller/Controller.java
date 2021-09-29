package by.epam.afc.controller;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.CommandProvider;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static by.epam.afc.controller.RequestAttribute.COMMAND;
import static by.epam.afc.controller.SessionAttribute.LATEST_CONTEXT_PATH;
import static by.epam.afc.controller.command.Router.DispatchType.REDIRECT;

@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandProvider commandProvider = CommandProvider.getInstance();
        String commandName = request.getParameter(COMMAND);
        Optional<Command> commandOptional = commandProvider.defineCommand(commandName);
        Router router;
        if (commandOptional.isPresent()) {
            Command command = commandOptional.get();
            router = command.execute(request, response);
        } else {
            HttpSession session = request.getSession();
            String latestPath = (String) session.getAttribute(LATEST_CONTEXT_PATH);
            logger.warn("Unknown command: " + (commandName == null ? "null" : commandName));
            router = new Router(REDIRECT, request.getContextPath() + latestPath);
        }
        switch (router.getDispatchType()) {
            case FORWARD:
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.getTargetPath());
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(router.getTargetPath());
                break;
            case NOT_REQUIRED:
                break;
            default:
                logger.error("Invalid router type!");
                response.sendError(500);
        }
    }
}