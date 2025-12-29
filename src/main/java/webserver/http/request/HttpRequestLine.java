package webserver.http.request;

public class HttpRequestLine {

    private final HttpMethod method;
    private final HttpVersion version;
    private final RequestUrl requestUrl;

    public HttpRequestLine(HttpMethod method, HttpVersion version, RequestUrl requestUrl) {
        this.method = method;
        this.version = version;
        this.requestUrl = requestUrl;
    }
}
