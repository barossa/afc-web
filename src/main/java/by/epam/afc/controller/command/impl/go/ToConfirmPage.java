package by.epam.afc.controller.command.impl.go;

import by.epam.afc.controller.command.Command;
import by.epam.afc.controller.command.Router;
import by.epam.afc.dao.entity.User;
import by.epam.afc.service.util.CodeGenerator;
import by.epam.afc.service.util.MailSender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

import static by.epam.afc.controller.PagePath.CONFIRMATION_PAGE;
import static by.epam.afc.controller.RequestAttribute.EMAIL;
import static by.epam.afc.controller.SessionAttribute.*;
import static by.epam.afc.controller.command.Router.DispatchType.FORWARD;

public class ToConfirmPage implements Command {
    private static final int DISPLAYING_SYMBOLS = 5;
    private static final int EMAIL_NAME_PART = 0;
    private static final int EMAIL_DOMAIN = 1;
    private static final String AT = "@";
    private static final String BLOCKED_SYMBOLS = "*****";

    private static final String SUBJECT = "Verification";
    private static final String TEXT = "Your verification code is: ";

    /*Countdown time in seconds*/
    private static final long COUNTDOWN_SECONDS = 30;

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        request.setAttribute(EMAIL, formEmail(user.getEmail()));

        Integer verificationCode = (Integer) session.getAttribute(VERIFICATION_CODE);
        MailSender mailSender = new MailSender();
        if (verificationCode == null) {
            CodeGenerator codeGenerator = CodeGenerator.getInstance();
            Integer code = codeGenerator.generate();
            mailSender.send(user.getEmail(), TEXT + code, SUBJECT);
            session.setAttribute(VERIFICATION_CODE, code);
            session.setAttribute(SENT_TIME, LocalDateTime.now());
        } else {
            LocalDateTime sentTime = (LocalDateTime) session.getAttribute(SENT_TIME);
            LocalDateTime mailAvailable = sentTime.plusSeconds(COUNTDOWN_SECONDS);
            LocalDateTime currentTime = LocalDateTime.now();
            if (currentTime.isAfter(mailAvailable)) {
                mailSender.send(user.getEmail(), TEXT + verificationCode, SUBJECT);
                session.setAttribute(SENT_TIME, LocalDateTime.now());
            }
        }
        return new Router(FORWARD, CONFIRMATION_PAGE);
    }

    private String formEmail(String email) {
        String[] emailParts = email.split(AT);
        String emailName;
        if (emailParts[EMAIL_NAME_PART].length() <= DISPLAYING_SYMBOLS) {
            emailName = emailParts[EMAIL_NAME_PART];
        } else {
            emailName = emailParts[EMAIL_NAME_PART].substring(0, DISPLAYING_SYMBOLS);
        }
        return emailName + BLOCKED_SYMBOLS + AT + emailParts[EMAIL_DOMAIN];
    }

}
