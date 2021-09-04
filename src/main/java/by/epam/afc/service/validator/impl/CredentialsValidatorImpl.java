package by.epam.afc.service.validator.impl;

import by.epam.afc.service.validator.CredentialsValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialsValidatorImpl implements CredentialsValidator {
    private static final String LOGIN_REGEX = "^(?=.*[A-Za-z0-9]$)[A-Za-z]\\w{0,19}$";
    private static final String EMAIL_REGEX = "^[A-Za-z][._]{0,19}.+@[A-Za-z]+.*\\..*[A-Za-z]$";
    private static final String PHONE_REGEX = "^\\+?\\d{10,15}$";
    private static final String PASSWORD_REGEX = "^[^ ]{5,30}$";

    private static final int MAX_LOGIN_LENGTH = 20;
    private static final int MAX_EMAIL_LENGTH = 100;

    private static final CredentialsValidatorImpl instance = new CredentialsValidatorImpl();

    private final Pattern loginPattern;
    private final Pattern emailPattern;
    private final Pattern phonePattern;
    private final Pattern passPattern;

    private CredentialsValidatorImpl(){
        loginPattern = Pattern.compile(LOGIN_REGEX);
        emailPattern = Pattern.compile(EMAIL_REGEX);
        phonePattern = Pattern.compile(PHONE_REGEX);
        passPattern = Pattern.compile(PASSWORD_REGEX);
    }

    public static CredentialsValidatorImpl getInstance(){
        return instance;
    }

    @Override
    public boolean validateLogin(String login) {
        if (login != null && login.length() <= MAX_LOGIN_LENGTH){
            Matcher matcher = loginPattern.matcher(login);
            return matcher.matches();
        }
        return false;
    }

    @Override
    public boolean validateEmail(String email) {
        if (email != null && email.length() <= MAX_EMAIL_LENGTH){
            Matcher matcher = emailPattern.matcher(email);
            return matcher.matches();
        }
        return false;
    }

    @Override
    public boolean validatePhone(String phone) {
        if (phone != null && !phone.isEmpty()){
           Matcher matcher = phonePattern.matcher(phone);
           return matcher.matches();
        }
        return false;
    }

    @Override
    public boolean validatePassword(String password) {
        if (password != null && !password.isEmpty()){
            Matcher matcher = passPattern.matcher(password);
            return matcher.matches();
        }
        return false;
    }
}
