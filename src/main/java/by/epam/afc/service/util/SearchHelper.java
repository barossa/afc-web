package by.epam.afc.service.util;

public class SearchHelper {
    private static final SearchHelper instance = new SearchHelper();
    private static final String REGEXP_OR = "|";
    private static final String SPACE = " ";

    private SearchHelper(){}

    public static SearchHelper getInstance(){
        return instance;
    }

    public String completeRegex(String searchRequest){
        String[] words = searchRequest.split(SPACE);
        StringBuilder regex = new StringBuilder();

        for(int i = 0; i < words.length; i++){
            regex.append(words[i].toUpperCase());
            if(i != words.length - 1){
                regex.append(REGEXP_OR);
            }
        }
        return regex.toString();
    }

}
