package by.epam.afc.service.validator;

import java.util.Map;

/**
 * The interface Credentials validator.
 */
public interface CredentialsValidator {
    /**
     * Validate name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    boolean validateName(String name);

    /**
     * Validate login boolean.
     *
     * @param login the login
     * @return the boolean
     */
    boolean validateLogin(String login);

    /**
     * Validate email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean validateEmail(String email);

    /**
     * Validate phone boolean.
     *
     * @param phone the phone
     * @return the boolean
     */
    boolean validatePhone(String phone);

    /**
     * Validate password boolean.
     *
     * @param password the password
     * @return the boolean
     */
    boolean validatePassword(String password);

    /**
     * Validate about boolean.
     *
     * @param about the about
     * @return the boolean
     */
    boolean validateAbout(String about);

    /**
     * Validate status boolean.
     *
     * @param status the status
     * @return the boolean
     */
    boolean validateStatus(String status);

    /**
     * Validate role boolean.
     *
     * @param role the role
     * @return the boolean
     */
    boolean validateRole(String role);

    /**
     * Validate credentials map.
     *
     * @param credentialsMap the credentials map
     * @return the map
     */
    Map<String, String> validateCredentials(Map<String, String> credentialsMap);
}
