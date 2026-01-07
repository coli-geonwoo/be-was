package webserver.resolver;

import http.response.ResponseCookie;
import java.util.Map;
import java.util.StringJoiner;

public class HttpResponseCookieResolver implements HttpResponseResolver<ResponseCookie> {

    private static final String COOKIE_CONTENT_DELIMITER = "=";
    private static final String END_COOKIE_CONTENT_DELIMITER = "; ";
    private static final String MAX_AGE_CONTENT_DELIMITER = "Max-Age";
    private static final String PATH_PREFIX = "Path=";

    @Override
    public String resolve(ResponseCookie responseCookie) {
        Map<String, String> contents = responseCookie.getContents();
        String path = responseCookie.getPath();
        StringJoiner cookieContent = new StringJoiner(END_COOKIE_CONTENT_DELIMITER);
        contents.forEach((key, value) -> {
            cookieContent.add(key + COOKIE_CONTENT_DELIMITER + value);
        });

        int maxAge = responseCookie.getMaxAge();
        cookieContent.add(MAX_AGE_CONTENT_DELIMITER + COOKIE_CONTENT_DELIMITER + maxAge);

        return cookieContent
                + END_COOKIE_CONTENT_DELIMITER
                + PATH_PREFIX
                + path;
    }
}
