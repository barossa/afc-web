package by.epam.afc.service.util;

/**
 * The type Search helper.
 */
public class SearchHelper {
    private static final SearchHelper instance = new SearchHelper();
    private static final String REGEXP_OR = "|";
    private static final String SPACE = " ";

    private SearchHelper() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SearchHelper getInstance() {
        return instance;
    }

    /**
     * Complete regex string.
     *
     * @param searchRequest the search request
     * @return the string
     */
    public String completeRegex(String searchRequest) {
        String[] words = searchRequest.split(SPACE);
        StringBuilder regex = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            regex.append(words[i].toUpperCase());
            if (i != words.length - 1) {
                regex.append(REGEXP_OR);
            }
        }
        return regex.toString();
    }

}
