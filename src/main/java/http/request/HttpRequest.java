package http.request;

import http.HttpMethod;

public class HttpRequest {

    private final HttpRequestLine requestLine;
    private final HttpRequestHeader requestHeader;
    private final HttpRequestBody requestBody;
    private final RequestCookie requestCookie;

    public HttpRequest(
            HttpRequestLine requestLine,
            HttpRequestHeader requestHeader,
            HttpRequestBody requestBody,
            RequestCookie requestCookie
    ) {
        validateMethodAndBody(requestLine, requestBody);
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.requestCookie = requestCookie;
    }

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeader requestHeader, RequestCookie requestCookie) {
        this(requestLine, requestHeader, HttpRequestBody.EMPTY_REQUEST_BODY, requestCookie);
    }

    //TODO 테스트 추가
    private void validateMethodAndBody(HttpRequestLine requestLine, HttpRequestBody requestBody) {
        HttpMethod httpMethod = requestLine.getMethod();
        if (requestBody != HttpRequestBody.EMPTY_REQUEST_BODY && httpMethod != HttpMethod.POST) {
            throw new RuntimeException("Method " + requestLine.getMethod() + " not supported");
        }
    }

    public boolean isGet() {
        return requestLine.isGet();
    }

    public boolean isPost() {
        return requestLine.isPost();
    }

    public boolean hasCookie(String key) {
        return requestCookie.containsKey(key);
    }

    public String getRequestParameter(String parameterName) {
        return requestLine.getRequestParam(parameterName);
    }

    public HttpMethod getRequestMethod() {
        return requestLine.getMethod();
    }

    public HttpRequestLine getRequestLine() {
        return requestLine;
    }

    public HttpRequestHeader getRequestHeader() {
        return requestHeader;
    }

    public HttpRequestBody getRequestBody() {
        return requestBody;
    }

    public String getRequestUrl() {
        return requestLine.getRequestUrl();
    }

    public RequestCookie getRequestCookie() {
        return requestCookie;
    }
}
