package by.epam.afc.service.validator;

/**
 * The interface Search request validator.
 */
public interface SearchRequestValidator {
    /**
     * Validate request boolean.
     *
     * @param search the search
     * @return the boolean
     */
    boolean validateRequest(String search);
}
