package by.epam.afc.service.validator;

import by.epam.afc.service.validator.impl.NumberValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberValidatorImplTest {

    @ParameterizedTest
    @ValueSource(strings = {"0","1","2","3","17"})
    public void correctNumberTest(String number){
        NumberValidator numberValidator = NumberValidatorImpl.getInstance();
        boolean actual = numberValidator.validateNumber(number);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1","-923","0,3123","-0.5","0.3"})
    public void incorrectNumberTest(String number){
        NumberValidator numberValidator = NumberValidatorImpl.getInstance();
        boolean actual = numberValidator.validateNumber(number);
        Assertions.assertFalse(actual);
    }

}
