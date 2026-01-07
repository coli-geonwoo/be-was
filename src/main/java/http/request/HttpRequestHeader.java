package http.request;

import java.util.Map;

public class HttpRequestHeader {

    private final Map<String, String> headers;

    public HttpRequestHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean containsHeader(String headerName) {
        return headers.containsKey(headerName);
    }

    public String getHeaderContent(String headerKey) {
        return headers.get(headerKey);
    }
}
