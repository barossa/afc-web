package by.epam.afc.service.validator;

import by.epam.afc.dao.entity.User;

import java.util.Map;

public interface CredentialsValidator {
    boolean validateName(String name);
    boolean validateLogin(String login);
    boolean validateEmail(String email);
    boolean validatePhone(String phone);
    boolean validatePassword(String password);
    Map<String, String> validateCredentials(Map<String, String> credentialsMap);
}
