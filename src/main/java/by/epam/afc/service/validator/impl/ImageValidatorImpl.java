package by.epam.afc.service.validator.impl;

import by.epam.afc.service.validator.ImageValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageValidatorImpl implements ImageValidator {
    private static final ImageValidatorImpl instance = new ImageValidatorImpl();

    private static final String IMAGE_REGEX = "^data:image/[a-zA-Z]{1,5};base64,.{100,}$";

    private final Pattern imagePattern;

    private ImageValidatorImpl() {
        imagePattern = Pattern.compile(IMAGE_REGEX, Pattern.CASE_INSENSITIVE);
    }

    public static ImageValidatorImpl getInstance(){
        return instance;
    }

    @Override
    public boolean validateImage(String base64) {
        if(base64 != null){
            Matcher matcher = imagePattern.matcher(base64);
            return matcher.find();
        }
        return false;
    }
}
