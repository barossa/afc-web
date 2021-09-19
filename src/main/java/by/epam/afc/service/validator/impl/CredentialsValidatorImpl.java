package by.epam.afc.service.validator.impl;

import by.epam.afc.service.validator.CredentialsValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialsValidatorImpl implements CredentialsValidator {
    private static final String NAME_REGEX = "^[А-Яа-яA-Za-z]+$";
    private static final String LOGIN_REGEX = "^(?=.*[A-Za-z0-9]$)[A-Za-z]\\w{0,19}$";
    private static final String EMAIL_REGEX = "^[A-Za-z][._]{0,19}.+@[A-Za-z]+.*\\..*[A-Za-z]$";
    private static final String PHONE_REGEX = "^\\+?\\d{10,15}$";
    private static final String PASSWORD_REGEX = "^[^ ]{5,30}$";

    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int MAX_EMAIL_LENGTH = 100;

    private static final CredentialsValidatorImpl instance = new CredentialsValidatorImpl();

    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String LOGIN = "login";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String PASSWORD = "password";
    public static final String PASSWORD_REPEAT = "passwordRepeat";

    private final Pattern namePattern;
    private final Pattern loginPattern;
    private final Pattern emailPattern;
    private final Pattern phonePattern;
    private final Pattern passPattern;

    private CredentialsValidatorImpl() {
        namePattern = Pattern.compile(NAME_REGEX);
        loginPattern = Pattern.compile(LOGIN_REGEX);
        emailPattern = Pattern.compile(EMAIL_REGEX);
        phonePattern = Pattern.compile(PHONE_REGEX);
        passPattern = Pattern.compile(PASSWORD_REGEX);
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
    public Map<String, String> validateCredentials(Map<String, String> credentialsMap) {
        Map<String, String> correctCredentials = new HashMap<>(credentialsMap);

        if (!validateName(correctCredentials.get(FIRSTNAME))) {
            correctCredentials.put(FIRSTNAME, "");
        }
        if (!validateName(correctCredentials.get(LASTNAME))) {
            correctCredentials.put(LASTNAME, "");
        }
        if (!validateLogin(correctCredentials.get(LOGIN))) {
            correctCredentials.put(LOGIN, "");
        }
        if (!validateEmail(correctCredentials.get(EMAIL))) {
            correctCredentials.put(EMAIL, "");
        }
        if (!validatePhone(correctCredentials.get(PHONE))) {
            correctCredentials.put(PHONE, "");
        }

        String password = correctCredentials.get(PASSWORD);
        String passwordRepeat = correctCredentials.get(PASSWORD_REPEAT);
        if (!password.equals(passwordRepeat)) {
            correctCredentials.put(PASSWORD, "");
            correctCredentials.put(PASSWORD_REPEAT, "");
        }

        return correctCredentials;
    }
}
