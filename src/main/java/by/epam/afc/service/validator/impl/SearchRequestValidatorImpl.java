package by.epam.afc.service.validator.impl;

import by.epam.afc.service.validator.SearchRequestValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchRequestValidatorImpl implements SearchRequestValidator {
    private static final SearchRequestValidatorImpl instance = new SearchRequestValidatorImpl();

    private static final String SEARCH_REQUEST_REGEXP = "^(?=.*[A-Za-zА-Яа-я]$)([A-Za-zА-Яа-я]+_?)+$";
    private static final int SEARCH_REQUEST_MAX_LENGTH = 30;
    public static final String SPLIT_CHARACTER = "_";

    private final Pattern requestPattern;

    private SearchRequestValidatorImpl() {
        requestPattern = Pattern.compile(SEARCH_REQUEST_REGEXP);
    }

    public static SearchRequestValidatorImpl getInstance() {
        return instance;
    }

    @Override
    public boolean validateRequest(String search) {
        if (search != null) {
            if (search.length() > 0 && search.length() <= SEARCH_REQUEST_MAX_LENGTH) {
                search = search.replaceAll(" ", SPLIT_CHARACTER);
                /*Java 8 doesn't support whitespaces in regex*/
                Matcher matcher = requestPattern.matcher(search);
                return matcher.matches();
            }
        }
        System.out.println("INCORRECT SEARCH REQUEST");
        // FIXME: 12/6/21 DELETE System.out
        return false;
    }
}
