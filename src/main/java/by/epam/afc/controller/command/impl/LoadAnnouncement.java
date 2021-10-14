package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.controller.command.pagination.AnnouncementPagination;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.service.validator.impl.NumberValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static by.epam.afc.controller.PagePath.ANNOUNCEMENT_PAGE;
import static by.epam.afc.controller.PagePath.INDEX;
import static by.epam.afc.controller.RequestAttribute.ID;
import static by.epam.afc.controller.SessionAttribute.CURRENT_ANNOUNCEMENT;
import static by.epam.afc.controller.SessionAttribute.PAGINATION_DATA;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;
import static by.epam.afc.controller.command.Router.DispatchType.REDIRECT;

public class LoadAnnouncement implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        AnnouncementPagination pagination = (AnnouncementPagination) session.getAttribute(PAGINATION_DATA);

        if (pagination != null && !pagination.isEmpty()) {
            String idParameter = request.getParameter(ID);
            NumberValidatorImpl numberValidator = NumberValidatorImpl.getInstance();
            boolean valid = numberValidator.validateNumber(idParameter);

            if (valid) {
                int id = Integer.parseInt(idParameter);
                List<Announcement> currentData = pagination.getCurrentData();
                if (currentData.size() > id) {
                    session.setAttribute(CURRENT_ANNOUNCEMENT, currentData.get(id));
                }
                return new Router(FORWARD, ANNOUNCEMENT_PAGE);
            }

        }
        return new Router(REDIRECT, request.getContextPath() + INDEX);
    }
}
