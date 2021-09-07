package by.epam.afc.controller.listener;

import by.epam.afc.dao.entity.User;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import static by.epam.afc.controller.RequestAttribute.LOCALE;
import static by.epam.afc.controller.SessionAttribute.*;
import static by.epam.afc.dao.entity.User.Role.GUEST;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        User guest = User.getBuilder()
                .role(GUEST)
                .build();
        session.setAttribute(USER, guest);
        session.setAttribute(AUTHORIZED, false);
        session.setAttribute(LANGUAGE, "English(US)");
        session.setAttribute(LOCALE, "en_US");
    }
}
