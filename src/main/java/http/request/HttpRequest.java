package http.request;

import http.HttpMethod;
import webserver.exception.RequestProcessingException;

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
        this.requestLine = requestLine;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.requestCookie = requestCookie;
    }

    public HttpRequest(HttpRequestLine requestLine, HttpRequestHeader requestHeader, RequestCookie requestCookie) {
        this(requestLine, requestHeader, HttpRequestBody.EMPTY_REQUEST_BODY, requestCookie);
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
