package http.response;

import java.util.List;
import java.util.Map;

public class HttpResponseHeader {

    private final Map<String, List<String>> headers;

    public HttpResponseHeader(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }
}
