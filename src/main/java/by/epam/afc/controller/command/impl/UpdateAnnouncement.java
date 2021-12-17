package by.epam.afc.controller.command.impl;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.Announcement;
import by.epam.afc.dao.entity.User;
import by.epam.afc.exception.ServiceException;
import by.epam.afc.service.impl.AnnouncementServiceImpl;
import by.epam.afc.service.util.RequestParameterConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.epam.afc.controller.PagePath.*;
import static by.epam.afc.controller.RequestAttribute.EXCEPTION_MESSAGE;
import static by.epam.afc.controller.SessionAttribute.USER;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class UpdateAnnouncement implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateAnnouncement.class);

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            RequestParameterConverter parameterConverter = RequestParameterConverter.getInstance();
            Map<String, List<String>> parameterMap = parameterConverter.transform(request.getParameterMap());
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            AnnouncementServiceImpl announcementService = AnnouncementServiceImpl.getInstance();
            Optional<Announcement> updateAnnouncement = announcementService.updateAnnouncement(parameterMap, user);
            if (updateAnnouncement.isPresent()) {
                return new Router(FORWARD, MY_ANNOUNCEMENTS_PAGE);
            }
            return new Router(FORWARD, SUBMIT_AD_PAGE);
        } catch (ServiceException e) {
            logger.error("Error occurred while submitting announcement", e);
            request.setAttribute(EXCEPTION_MESSAGE, "Error occurred while submitting announcement");
            return new Router(FORWARD, request.getContextPath() + ERROR_500);
        }
    }
}
