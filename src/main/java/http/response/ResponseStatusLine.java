package http.response;

import http.HttpStatusCode;
import http.HttpVersion;

public class ResponseStatusLine {

    private final HttpVersion httpVersion;
    private final HttpStatusCode statusCode;

    public ResponseStatusLine(HttpVersion httpVersion, HttpStatusCode statusCode) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }
}
