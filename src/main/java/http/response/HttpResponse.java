package http.response;

public class HttpResponse {

    private final HttpStatusCode statusCode;
    private final HttpResponseHeader headers;
    private final HttpResponseBody body;

    public HttpResponse(HttpStatusCode statusCode, HttpResponseHeader headers, HttpResponseBody body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }
}
