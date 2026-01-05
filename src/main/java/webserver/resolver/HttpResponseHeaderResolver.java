package webserver.resolver;

import http.response.HttpResponseHeader;
import java.util.Map;
import java.util.StringJoiner;

class HttpResponseHeaderResolver implements HttpResponseResolver<HttpResponseHeader> {

    private static final String RESPONSE_HEADER_CONTENT_DELIMITER = "\r\n";
    private static final String RESPONSE_HEADER_KEY_VALUE_DELIMITER = ": ";

    @Override
    public String resolve(HttpResponseHeader headers) {
        StringJoiner joiner = new StringJoiner(RESPONSE_HEADER_CONTENT_DELIMITER);
        Map<String, String> headerContent = headers.getHeaders();

        headerContent.forEach((key, value) -> joiner.add(key
                + RESPONSE_HEADER_KEY_VALUE_DELIMITER
                + value
        ));
        return joiner.toString();
    }
}
