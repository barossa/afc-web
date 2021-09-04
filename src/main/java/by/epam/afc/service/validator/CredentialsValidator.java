package by.epam.afc.service.validator;

public interface CredentialsValidator {
    boolean validateLogin(String login);
    boolean validateEmail(String email);
    boolean validatePhone(String phone);
    boolean validatePassword(String password);
}
