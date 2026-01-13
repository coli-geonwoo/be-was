package webserver.parse.request;

import http.request.RequestCookie;
import java.util.HashMap;
import java.util.Map;

public class RequestCookieParser implements HttpRequestParser<RequestCookie> {

    private static final int COOKIE_KEY_INDEX = 0;
    private static final int COOKIE_VALUE_INDEX = 1;
    private static final String COOKIE_TOKEN_DELIMITER = ";";
    private static final String COOKIE_KEY_VALUE_DELIMITER = "=";

    @Override
    public RequestCookie parse(String input) {
        Map<String, String> contents = new HashMap<>();
        String[] tokens = input.split(COOKIE_TOKEN_DELIMITER);
        for (String token : tokens) {
            String[] splitToken = token.trim().split(COOKIE_KEY_VALUE_DELIMITER);
            String key = splitToken[COOKIE_KEY_INDEX];
            String value = splitToken[COOKIE_VALUE_INDEX];
            contents.put(key, value);
        }

        return new RequestCookie(contents);
    }
}
