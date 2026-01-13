package webserver.parse.request;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import http.request.HttpRequestHeader;

class HttpRequestHeaderParser implements HttpRequestParser<HttpRequestHeader> {

    private static final String HTTP_REQUEST_HEADER_DELIMITER = ":";

    @Override
    public HttpRequestHeader parse(String input) {
        Map<String, String> headers = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(input, System.lineSeparator());
        while (tokenizer.hasMoreTokens()) {
            String headerContents = tokenizer.nextToken();
            int firstDelimiterIndex = headerContents.indexOf(HTTP_REQUEST_HEADER_DELIMITER);
            if (firstDelimiterIndex == -1) {
                break;
            }
            String key = headerContents.substring(0, firstDelimiterIndex).trim().toLowerCase();
            String value = headerContents.substring(firstDelimiterIndex + 1).trim().toLowerCase();
            headers.put(key, value);
        }
        return new HttpRequestHeader(headers);
    }
}
