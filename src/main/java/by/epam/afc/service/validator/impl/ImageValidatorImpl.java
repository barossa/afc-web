package by.epam.afc.service.validator.impl;

import by.epam.afc.service.validator.ImageValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The type Image validator.
 */
public class ImageValidatorImpl implements ImageValidator {
    private static final ImageValidatorImpl instance = new ImageValidatorImpl();

    private static final String IMAGE_REGEX = "^data:image/[a-zA-Z]{1,5};base64,.{100,}$";

    private final Pattern imagePattern;

    private ImageValidatorImpl() {
        imagePattern = Pattern.compile(IMAGE_REGEX, Pattern.CASE_INSENSITIVE);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ImageValidatorImpl getInstance() {
        return instance;
    }

    /**
     * Validate image boolean.
     *
     * @param base64 the base 64
     * @return the boolean
     */
    @Override
    public boolean validateImage(String base64) {
        if (base64 != null) {
            Matcher matcher = imagePattern.matcher(base64);
            return matcher.find();
        }
        return false;
    }
}
