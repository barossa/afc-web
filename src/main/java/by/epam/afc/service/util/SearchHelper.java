package by.epam.afc.service.util;

public class SearchHelper {
    private static final SearchHelper instance = new SearchHelper();

    private static final String SQL_REGEXP = "regexp";
    private static final String REGEXP_ZERO_MORE_TIMES = "*";
    private static final String REGEXP_ANY_CHARACTER = ".";
    private static final String OPENING_BRACKET = "(";
    private static final String CLOSING_BRACKET = ")";
    private static final String REGEXP_OR = "|";
    private static final String QUERY_END = ";";
    private static final String QUOTE = "'";
    private static final String SPACE = " ";

    private SearchHelper(){}

    public static SearchHelper getInstance(){
        return instance;
    }

    public String completeRegexpQuery(String query,String... patterns){
        StringBuilder regexpBuilder = new StringBuilder(query);
        regexpBuilder.append(SPACE);
        regexpBuilder.append(SQL_REGEXP);
        regexpBuilder.append(QUOTE);
        for (String pattern : patterns) {
            regexpBuilder.append(OPENING_BRACKET);
            regexpBuilder.append(pattern);
            regexpBuilder.append(CLOSING_BRACKET);
            regexpBuilder.append(REGEXP_ZERO_MORE_TIMES);
            regexpBuilder.append(REGEXP_ANY_CHARACTER);
            regexpBuilder.append(REGEXP_ZERO_MORE_TIMES);
        }
        regexpBuilder.append(QUOTE);
        regexpBuilder.append(QUERY_END);
        return regexpBuilder.toString();
    }

    public String completeRegex(String searchRequest){
        String[] words = searchRequest.split(SPACE);
        StringBuilder regex = new StringBuilder();

        for(int i = 0; i < words.length; i++){
            regex.append(OPENING_BRACKET);
            regex.append(words[i]);
            regex.append(CLOSING_BRACKET);
            if(i != words.length - 1){
                regex.append(REGEXP_OR);
            }
        }

        return regex.toString();
    }

}
