package by.epam.afc.service.validator;

import by.epam.afc.service.validator.impl.SearchRequestValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class SearchRequestValidatorImplTest {

    @ParameterizedTest
    @MethodSource("correctRequests")
    public void correctSearchRequestTest(String request) {
        SearchRequestValidator searchRequestValidator = SearchRequestValidatorImpl.getInstance();
        boolean actual = searchRequestValidator.validateRequest(request);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("incorrectRequests")
    public void incorrectSearchRequestTest(String request) {
        SearchRequestValidator searchRequestValidator = SearchRequestValidatorImpl.getInstance();
        boolean actual = searchRequestValidator.validateRequest(request);
        Assertions.assertFalse(actual);
    }

    private static Stream<String> correctRequests() {
        return Stream.of("New laptop",
                "Drill bosh",
                "super gaming laptop",
                "ferrari italia",
                "some stuff");
    }

    private static Stream<String> incorrectRequests() {
        return Stream.of("2 cup of tea",
                "black+bird",
                "black  jack",
                "some-stuff");
    }

}
