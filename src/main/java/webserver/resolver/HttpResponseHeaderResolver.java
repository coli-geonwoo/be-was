package webserver.resolver;

import http.response.HttpResponseHeader;
import java.util.Map;
import java.util.StringJoiner;

class HttpResponseHeaderResolver implements HttpResponseResolver<HttpResponseHeader> {

    private static final String RESPONSE_HEADER_CONTENT_DELIMITER = ": ";

    @Override
    public String resolve(HttpResponseHeader headers) {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        Map<String, String> headerContent = headers.getHeaders();

        headerContent.entrySet()
                .forEach(
                        entry -> joiner.add(entry.getKey()
                                + RESPONSE_HEADER_CONTENT_DELIMITER
                                + entry.getValue()
                        )
                );
        return joiner.toString();
    }
}
