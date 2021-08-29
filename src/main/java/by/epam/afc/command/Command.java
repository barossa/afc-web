package by.epam.afc.command;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request);
}
