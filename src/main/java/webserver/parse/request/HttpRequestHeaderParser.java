package webserver.parse.request;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import http.request.HttpRequestHeader;

class HttpRequestHeaderParser implements HttpRequestParser<HttpRequestHeader> {

    private static final String HTTP_REQUEST_HEADER_DELIMITER = ":";
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 0;

    @Override
    public HttpRequestHeader parse(String input) {
        Map<String, String> headers = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(input, System.lineSeparator());
        while (tokenizer.hasMoreTokens()) {
            String headerContents = tokenizer.nextToken();
            String [] parsedHeaderContents = headerContents.split(HTTP_REQUEST_HEADER_DELIMITER);
            String key = parsedHeaderContents[HEADER_KEY_INDEX].strip();
            String value = parsedHeaderContents[HEADER_VALUE_INDEX].strip();
            headers.put(key, value);
        }
        return new HttpRequestHeader(headers);
    }
}
