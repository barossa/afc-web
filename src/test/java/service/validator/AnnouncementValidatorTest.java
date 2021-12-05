package service.validator;

import by.epam.afc.service.validator.AnnouncementValidator;
import by.epam.afc.service.validator.impl.AnnouncementValidatorImpl;
import by.epam.afc.service.validator.impl.CredentialsValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class AnnouncementValidatorTest {

    @ParameterizedTest
    @MethodSource("invalidTitles")
    public void invalidTitleTest(String title){
        AnnouncementValidator validator = AnnouncementValidatorImpl.getInstance();
        boolean actual = validator.validateTitle(title);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("validTitles")
    public void validTitleTest(String title){
        AnnouncementValidator validator = AnnouncementValidatorImpl.getInstance();
        boolean actual = validator.validateTitle(title);
        Assertions.assertTrue(actual);
    }

    private static Stream<String> invalidTitles(){
        return Stream.of(" bla!",
                "",
                "1234",
                "1234567890123456789987654321123456789",
                " ",
                "qwer ty");
    }

    private static Stream<String> validTitles(){
        return Stream.of("12345",
                "qwerty",
                "765ghjs",
                "defaultannouncement!",
                "___###announcement",
                "-_-_-_==");
    }

    @ParameterizedTest
    @MethodSource("invalidDescriptions")
    public void invalidDescriptionTest(String description){
        AnnouncementValidator validator = AnnouncementValidatorImpl.getInstance();
        boolean actual = validator.validateDescription(description);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("validDescriptions")
    public void validDescriptionTest(String description){
        AnnouncementValidator validator = AnnouncementValidatorImpl.getInstance();
        boolean actual = validator.validateDescription(description);
        Assertions.assertTrue(actual);
    }

    private static Stream<String> invalidDescriptions(){
        return Stream.of(" bla!",
                "",
                "Description;",
                "Product for best price>",
                " ",
                "qwer ty");
    }

    private static Stream<String> validDescriptions(){
        return Stream.of("12345",
                "qwerty",
                "765ghjs",
                "defaultannouncement!",
                "___###announcement",
                "-_-_-_==");
    }
}
