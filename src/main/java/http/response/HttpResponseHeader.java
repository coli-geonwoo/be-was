package http.response;

import java.util.List;
import java.util.Map;

public class HttpResponseHeader {

    private final Map<String, String> headers;

    public HttpResponseHeader(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
