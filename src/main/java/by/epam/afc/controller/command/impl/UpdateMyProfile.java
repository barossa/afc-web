package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateMyProfile implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        //// TODO: 12/11/21 CLIENT&SERVER VALIDATION
        return null;
    }
}
