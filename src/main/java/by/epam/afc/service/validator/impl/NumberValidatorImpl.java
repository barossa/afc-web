package by.epam.afc.service.validator.impl;

import by.epam.afc.service.validator.NumberValidator;

public class NumberValidatorImpl implements NumberValidator {
    private static final NumberValidatorImpl instance = new NumberValidatorImpl();

    private NumberValidatorImpl() {
    }

    public static NumberValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateNumber(String number) {
        if (number != null) {
            try {
                int value = Integer.parseInt(number);
                return value >= 0;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
}
