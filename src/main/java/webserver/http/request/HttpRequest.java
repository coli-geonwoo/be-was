package webserver.http.request;

public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpRequestHeader requestHeader;

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeader requestHeader) {
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
    }
}
