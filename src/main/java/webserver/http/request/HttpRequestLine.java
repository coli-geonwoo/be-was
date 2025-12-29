package webserver.http.request;

public class HttpRequestLine {

    private final HttpMethod method;
    private final RequestUrl requestUrl;
    private final HttpVersion version;

    public HttpRequestLine(HttpMethod method, RequestUrl requestUrl, HttpVersion version) {
        this.method = method;
        this.requestUrl = requestUrl;
        this.version = version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getRequestUrl() {
        return requestUrl.getUrl();
    }
}
