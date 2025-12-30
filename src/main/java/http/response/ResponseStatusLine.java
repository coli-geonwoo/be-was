package http.response;

import http.request.HttpVersion;

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
