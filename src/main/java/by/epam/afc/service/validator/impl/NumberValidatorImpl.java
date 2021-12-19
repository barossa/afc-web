package by.epam.afc.service.validator.impl;

import by.epam.afc.service.validator.NumberValidator;

/**
 * The type Number validator.
 */
public class NumberValidatorImpl implements NumberValidator {
    private static final NumberValidatorImpl instance = new NumberValidatorImpl();

    private NumberValidatorImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static NumberValidatorImpl getInstance() {
        return instance;
    }

    /**
     * Validate number boolean.
     *
     * @param number the number
     * @return the boolean
     */
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
