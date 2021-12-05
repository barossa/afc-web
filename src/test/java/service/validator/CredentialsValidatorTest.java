package service.validator;

import by.epam.afc.service.validator.impl.CredentialsValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import sun.nio.ch.ThreadPool;

import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class CredentialsValidatorTest {

    @ParameterizedTest
    @MethodSource("invalidPasswords")
    public void invalidPasswordTest(String password){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validatePassword(password);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("correctPasswords")
    public void correctPasswordTest(String password){
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        boolean actual = validator.validatePassword(password);
        Assertions.assertTrue(actual);
    }

    private static Stream<String> invalidPasswords(){
        return Stream.of(" bla!",
                "",
                "1234",
                "1234567890123456789987654321123456789",
                " ",
                "qwer ty");
    }

    private static Stream<String> correctPasswords(){
        return Stream.of("12345",
                "qwerty",
                "765ghjs",
                "defaultpassword!",
                "___###password",
                "-_-_-_==");
    }
}
