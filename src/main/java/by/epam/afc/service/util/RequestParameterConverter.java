package by.epam.afc.service.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

}
