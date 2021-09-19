package by.epam.afc.service.util;

public class QueryHelper {
    private static final QueryHelper instance = new QueryHelper();

    private static final String SQL_REGEXP = "regexp";
    private static final String REGEXP_ANY_CHARACTER = ".";
    private static final String REGEXP_ZERO_MORE_TIMES = "*";
    private static final String OPENING_BRACKET = "(";
    private static final String CLOSING_BRACKET = ")";
    private static final String QUERY_END = ";";
    private static final String QUOTE = "'";
    private static final String SPACE = " ";


    private QueryHelper(){}

    public static QueryHelper getInstance(){
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

}
