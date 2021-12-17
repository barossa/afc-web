package by.epam.afc.service.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.epam.afc.controller.RequestAttribute.*;
import static by.epam.afc.service.validator.impl.CredentialsValidatorImpl.NOT_VALID;

public class RequestParameterConverter {
    private static final RequestParameterConverter instance = new RequestParameterConverter();

    private RequestParameterConverter() {
    }

    public static RequestParameterConverter getInstance(){
        return instance;
    }

    public Map<String, List<String>> transform(Map<String, String[]> parameterMap) {
        Map<String, List<String>> requestParams = parameterMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> Arrays.stream(e.getValue()).collect(Collectors.toList())));
        return requestParams;
    }

    public Map<String, String> singleValueMap(Map<String, List<String>> parameterMap){
        Map<String, String> singleValueMap = parameterMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        val -> val.getValue().stream()
                        .findFirst()
                        .orElse(NOT_VALID)));
        return singleValueMap;
    }

    public Map<String, String> findCredentials(Map<String, String[]> parameterMap){
        String id = getSingle(parameterMap.get(ID));
        String firstname = getSingle(parameterMap.get(FIRSTNAME));
        String lastname = getSingle(parameterMap.get(LASTNAME));
        String login = getSingle(parameterMap.get(LOGIN));
        String email = getSingle(parameterMap.get(EMAIL));
        String phone = getSingle(parameterMap.get(PHONE));
        String password = getSingle(parameterMap.get(PASSWORD));
        String passwordRepeat = getSingle(parameterMap.get(PASSWORD_REPEAT));
        String role = getSingle(parameterMap.get(ROLE));
        String status = getSingle(parameterMap.get(STATUS));
        String image = getSingle(parameterMap.get(IMAGE));
        String about = getSingle(parameterMap.get(ABOUT));
        Map<String, String> credentials = new HashMap<>();
        credentials.put(ID, id);
        credentials.put(FIRSTNAME, firstname);
        credentials.put(LASTNAME, lastname);
        credentials.put(LOGIN, login);
        credentials.put(EMAIL, email);
        credentials.put(PHONE, phone);
        credentials.put(PASSWORD, password);
        credentials.put(PASSWORD_REPEAT, passwordRepeat);
        credentials.put(ROLE, role);
        credentials.put(STATUS, status);
        credentials.put(ABOUT, about);
        credentials.put(IMAGE, image);
        return credentials;
    }

    public Map<String, String> findAnnouncementData(Map<String, String[]> parameterMap){
        Map<String, String> announcementData = new HashMap<>();
        String title = getSingle(parameterMap.get(TITLE));
        String price = getSingle(parameterMap.get(PRICE));
        String description = getSingle(parameterMap.get(DESCRIPTION));
        String region = getSingle(parameterMap.get(REGION));
        String category = getSingle(parameterMap.get(CATEGORY));
        announcementData.put(TITLE, title);
        announcementData.put(PRICE, price);
        announcementData.put(DESCRIPTION, description);
        announcementData.put(REGION, region);
        announcementData.put(CATEGORY, category);
        return announcementData;
    }

    private String getSingle(String[] params){
        if(params == null){
            return "";
        }
        return Arrays.stream(params).findFirst().orElse("");
    }

}
