package webserver.resolver;

import http.response.Cookie;
import java.util.Map;
import java.util.StringJoiner;

public class HttpResponseCookieResolver implements HttpResponseResolver<Cookie> {

    private static final String COOKIE_CONTENT_DELIMITER = "=";
    private static final String END_COOKIE_CONTENT_DELIMITER = "; ";
    private static final String MAX_AGE_CONTENT_DELIMITER = "Max-Age";
    private static final String PATH_PREFIX = "Path=";

    @Override
    public String resolve(Cookie cookie) {
        Map<String, String> contents = cookie.getContents();
        String path = cookie.getPath();
        StringJoiner cookieContent = new StringJoiner(END_COOKIE_CONTENT_DELIMITER);
        contents.forEach((key, value) -> {
            cookieContent.add(key + COOKIE_CONTENT_DELIMITER + value);
        });

        int maxAge = cookie.getMaxAge();
        cookieContent.add(MAX_AGE_CONTENT_DELIMITER + COOKIE_CONTENT_DELIMITER + maxAge);

        return cookieContent
                + END_COOKIE_CONTENT_DELIMITER
                + PATH_PREFIX
                + path;
    }
}
