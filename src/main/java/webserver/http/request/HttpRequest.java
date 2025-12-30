package webserver.http.request;

public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpRequestHeader requestHeader;
    private final HttpRequestBody requestBody;

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeader requestHeader, HttpRequestBody requestBody) {
        validateMethodAndBody(requestLine, requestBody);
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeader requestHeader) {
        this(requestLine, requestHeader, HttpRequestBody.EMPTY_REQUEST_BODY);
    }

    //TODO 테스트 추가
    private void validateMethodAndBody(HttpRequestLine requestLine, HttpRequestBody requestBody) {
        HttpMethod httpMethod = requestLine.getMethod();
        if (requestBody != HttpRequestBody.EMPTY_REQUEST_BODY && httpMethod != HttpMethod.POST) {
            throw new RuntimeException("Method " + requestLine.getMethod() + " not supported");
        }
    }

    public String getRequestUrl() {
        return requestLine.getRequestUrl();
    }
}
