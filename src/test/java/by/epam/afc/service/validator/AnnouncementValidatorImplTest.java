package by.epam.afc.service.validator;

import by.epam.afc.service.validator.impl.AnnouncementValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class AnnouncementValidatorImplTest {

    @ParameterizedTest
    @MethodSource("incorrectTitles")
    public void invalidTitleTest(String title) {
        AnnouncementValidator validator = AnnouncementValidatorImpl.getInstance();
        boolean actual = validator.validateTitle(title);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("correctTitles")
    public void validTitleTest(String title) {
        AnnouncementValidator validator = AnnouncementValidatorImpl.getInstance();
        boolean actual = validator.validateTitle(title);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("incorrectDescriptions")
    public void invalidDescriptionTest(String description) {
        AnnouncementValidator validator = AnnouncementValidatorImpl.getInstance();
        boolean actual = validator.validateDescription(description);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("correctDescriptions")
    public void validDescriptionTest(String description) {
        AnnouncementValidator validator = AnnouncementValidatorImpl.getInstance();
        boolean actual = validator.validateDescription(description);
        Assertions.assertTrue(actual);
    }

    private static Stream<String> incorrectTitles() {
        return Stream.of(" bla!",
                "(TITLE)",
                "1234_",
                "8888881234567890112312321313287892193921923123456789",
                "<script>alert('XSS!')</script>",
                "!_bloomberg");
    }

    private static Stream<String> correctTitles() {
        return Stream.of("House in the area",
                "Drill BOSH SR1000",
                "Hamburger XL",
                "default announcement title",
                "Harry Potter");
    }

    private static Stream<String> incorrectDescriptions() {
        return Stream.of("-> Pff!",
                " the history of my product...",
                "Description;",
                "Product for best price>",
                "<script>alert('WPC')</script>",
                "$1MLN High Rollers");
    }

    private static Stream<String> correctDescriptions() {
        return Stream.of("The best thing that i've ever seen!",
                "Tires 27.5 inches, without nipples",
                "Juicy homemade strawberry");
    }
}
