package by.epam.afc.service.validator.impl;

import by.epam.afc.dao.entity.User;
import by.epam.afc.service.validator.CredentialsValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.epam.afc.controller.RequestAttribute.*;

public class CredentialsValidatorImpl implements CredentialsValidator {
    private static final CredentialsValidatorImpl instance = new CredentialsValidatorImpl();

    private static final String NAME_REGEX = "^[А-Яа-яA-Za-z]+$";
    private static final String LOGIN_REGEX = "^(?=.*[A-Za-z0-9]$)[A-Za-z]\\w{0,19}$";
    private static final String EMAIL_REGEX = "^[A-Za-z][._]{0,19}.+@[A-Za-z]+.*\\..*[A-Za-z]$";
    private static final String PHONE_REGEX = "^\\+?\\d{10,15}$";
    private static final String PASSWORD_REGEX = "^[^ ]{5,30}$";
    private static final String ABOUT_REGEX = "^(?!.*[<>;]+.*$)[A-Za-zА-Яа-я]+.*$";

    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int MAX_EMAIL_LENGTH = 100;
    private static final int MAX_ABOUT_LENGTH = 200;

    public static final String NOT_VALID = "";

    private final Pattern namePattern;
    private final Pattern loginPattern;
    private final Pattern emailPattern;
    private final Pattern phonePattern;
    private final Pattern passPattern;
    private final Pattern aboutPattern;

    private CredentialsValidatorImpl() {
        namePattern = Pattern.compile(NAME_REGEX);
        loginPattern = Pattern.compile(LOGIN_REGEX);
        emailPattern = Pattern.compile(EMAIL_REGEX);
        phonePattern = Pattern.compile(PHONE_REGEX);
        passPattern = Pattern.compile(PASSWORD_REGEX);
        aboutPattern = Pattern.compile(ABOUT_REGEX);
    }

    public static CredentialsValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateName(String name) {
        if (name != null) {
            if (name.length() > 0 && name.length() <= MAX_NAME_LENGTH) {
                Matcher matcher = namePattern.matcher(name);
                return matcher.matches();
            }
        }
        return false;
    }

    @Override
    public boolean validateLogin(String login) {
        if (login != null) {
            if (login.length() > 0 && login.length() <= MAX_LOGIN_LENGTH) {
                Matcher matcher = loginPattern.matcher(login);
                return matcher.matches();
            }
        }
        return false;
    }

    @Override
    public boolean validateEmail(String email) {
        if (email != null) {
            if (email.length() > 0 && email.length() <= MAX_EMAIL_LENGTH) {
                Matcher matcher = emailPattern.matcher(email);
                return matcher.matches();
            }
        }
        return false;
    }

    @Override
    public boolean validatePhone(String phone) {
        if (phone != null) {
            if (phone.length() > 0) {
                Matcher matcher = phonePattern.matcher(phone);
                return matcher.matches();
            }
        }
        return false;
    }

    @Override
    public boolean validatePassword(String password) {
        if (password != null && password.length() > 0) {
            Matcher matcher = passPattern.matcher(password);
            return matcher.matches();
        }
        return false;
    }

    @Override
    public boolean validateAbout(String about) {
        if (about != null) {
            if (about.length() > 0 && about.length() <= MAX_ABOUT_LENGTH) {
                Matcher matcher = aboutPattern.matcher(about);
                return matcher.matches();
            }
        }
        return false;
    }

    @Override
    public boolean validateStatus(String status) {
        if (status == null) {
            return false;
        }
        try {
            User.Status.valueOf(status);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean validateRole(String role) {
        if (role == null) {
            return false;
        }
        try {
            User.Role.valueOf(role);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public Map<String, String> validateCredentials(Map<String, String> credentialsMap) {
        Map<String, String> correctCredentials = new HashMap<>(credentialsMap);

        if (!validateName(correctCredentials.get(FIRSTNAME))) {
            correctCredentials.put(FIRSTNAME, NOT_VALID);
        }
        if (!validateName(correctCredentials.get(LASTNAME))) {
            correctCredentials.put(LASTNAME, NOT_VALID);
        }
        if (!validateLogin(correctCredentials.get(LOGIN))) {
            correctCredentials.put(LOGIN, NOT_VALID);
        }
        if (!validateEmail(correctCredentials.get(EMAIL))) {
            correctCredentials.put(EMAIL, NOT_VALID);
        }
        if (!validatePhone(correctCredentials.get(PHONE))) {
            correctCredentials.put(PHONE, NOT_VALID);
        }

        String password = correctCredentials.get(PASSWORD);
        String passwordRepeat = correctCredentials.get(PASSWORD_REPEAT);
        if (!password.equals(passwordRepeat)) {
            correctCredentials.put(PASSWORD, NOT_VALID);
            correctCredentials.put(PASSWORD_REPEAT, NOT_VALID);
        }

        return correctCredentials;
    }
}
