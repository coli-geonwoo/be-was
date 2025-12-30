package http.response;

import http.request.HttpVersion;
import java.util.HashMap;

public class HttpResponse {

    private final ResponseStatusLine statusLine;
    private final HttpResponseHeader headers;
    private final HttpResponseBody body;

    public HttpResponse(ResponseStatusLine statusLine, HttpResponseHeader headers, HttpResponseBody body) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.body = body;
    }

    public HttpResponse(HttpResponseBody body) {
        this(
                new ResponseStatusLine(HttpVersion.HTTP_1_1, HttpStatusCode.OK_200),
                new HttpResponseHeader(new HashMap<>()),
                body
        );
    }

    public void addHeader(String key, String value) {
        headers.add(key, value);
    }

    public ResponseStatusLine getStatusLine() {
        return statusLine;
    }

    public HttpResponseHeader getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body.getBody();
    }
}
