package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class ForgotPassword implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return null;
    }
}
