package by.epam.afc.controller.listener;

import by.epam.afc.dao.entity.User;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute("locale", "en_US");
        User guest = User.getBuilder()
                .role(User.Role.GUEST)
                .build();
        session.setAttribute("user", guest);
    }
}
