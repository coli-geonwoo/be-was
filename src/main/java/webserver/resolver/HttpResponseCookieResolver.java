package webserver.resolver;

import http.response.Cookie;
import java.util.Map;

public class HttpResponseCookieResolver implements HttpResponseResolver<Cookie> {

    private static final String COOKIE_CONTENT_DELIMITER = "=";
    private static final String END_COOKIE_CONTENT_DELIMITER = "; ";
    private static final String PATH_PREFIX = "Path=";

    @Override
    public String resolve(Cookie cookie) {
        Map<String, String> contents = cookie.getContents();
        String path = cookie.getPath();
        StringBuilder cookieContent = new StringBuilder();
        contents.forEach((key, value) -> {
            cookieContent.append(key)
                    .append(COOKIE_CONTENT_DELIMITER)
                    .append(value);
        });
        return cookieContent
                + END_COOKIE_CONTENT_DELIMITER
                + PATH_PREFIX
                + path;
    }
}
