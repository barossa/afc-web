package by.epam.afc.service.validator;

import by.epam.afc.service.validator.impl.ImageValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class ImageValidatorImplTest {
    private static final int DATA_LENGTH = 150;
    private static final String BASE64_PREFIX = "data:image/jpeg;base64,";
    private String base64;

    @BeforeEach
    public void setUp() {
        base64 = BASE64_PREFIX + generateData();
    }

    @Test
    public void validateImageTest() {
        ImageValidator imageValidator = ImageValidatorImpl.getInstance();
        boolean actual = imageValidator.validateImage(base64);
        Assertions.assertTrue(actual);
    }

    private String generateData() {
        StringBuilder builder = new StringBuilder();
        new Random().ints(DATA_LENGTH)
                .forEach(builder::append);
        return builder.toString();
    }
}
