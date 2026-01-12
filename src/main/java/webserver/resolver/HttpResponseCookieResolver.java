package webserver.resolver;

import http.response.ResponseCookie;
import java.util.Map;
import java.util.StringJoiner;

public class HttpResponseCookieResolver implements HttpResponseResolver<ResponseCookie> {

    private static final String COOKIE_CONTENT_DELIMITER = "=";
    private static final String END_COOKIE_CONTENT_DELIMITER = "; ";
    private static final String MAX_AGE_CONTENT_DELIMITER = "Max-Age";
    private static final String PATH_PREFIX = "Path";
    private static final String SAME_SITE_PREFIX = "SameSite=";
    private static final String HTTP_ONLY_PREFIX = "HttpOnly";

    @Override
    public String resolve(ResponseCookie responseCookie) {
        Map<String, String> contents = responseCookie.getContents();
        String path = responseCookie.getPath();
        String sameSite = responseCookie.getSameSite().name();
        StringJoiner cookieContent = new StringJoiner(END_COOKIE_CONTENT_DELIMITER);
        int maxAge = responseCookie.getMaxAge();

        contents.forEach((key, value) ->
            cookieContent.add(key + COOKIE_CONTENT_DELIMITER + value)
        );

        if (responseCookie.isHttpOnly()) {
            cookieContent.add(HTTP_ONLY_PREFIX);
        }
        cookieContent.add(MAX_AGE_CONTENT_DELIMITER + COOKIE_CONTENT_DELIMITER + maxAge);
        cookieContent.add(PATH_PREFIX + COOKIE_CONTENT_DELIMITER + path);
        cookieContent.add(SAME_SITE_PREFIX + COOKIE_CONTENT_DELIMITER + sameSite);

        return cookieContent.toString();
    }
}
