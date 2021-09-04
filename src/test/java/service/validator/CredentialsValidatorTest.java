package service.validator;

import by.epam.afc.service.validator.impl.CredentialsValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sun.nio.ch.ThreadPool;

import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class CredentialsValidatorTest {

    @Test
    public void passwordValidationTest() throws InterruptedException {
        CredentialsValidatorImpl validator = CredentialsValidatorImpl.getInstance();
        String correctPassword = "testPassword123";
        String incorrectPassword = " bla!";
        boolean expectedTrue = validator.validatePassword(correctPassword);
        boolean expectedFalse = validator.validatePassword(incorrectPassword);
        Assertions.assertTrue(expectedTrue && !expectedFalse);
    }

}
