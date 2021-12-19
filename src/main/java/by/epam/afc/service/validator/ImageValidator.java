package by.epam.afc.service.validator;

/**
 * The interface Image validator.
 */
public interface ImageValidator {
    /**
     * Validate image boolean.
     *
     * @param base64 the base 64
     * @return the boolean
     */
    boolean validateImage(String base64);
}
