package http.response;

import http.request.HttpVersion;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final ResponseStatusLine statusLine;
    private final HttpResponseHeader headers;
    private final String viewName;
    private final HttpResponseBody body;
    private Cookie cookie;

    public HttpResponse(
            ResponseStatusLine statusLine,
            HttpResponseHeader headers,
            String viewName,
            HttpResponseBody body,
            Cookie cookie
    ) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.viewName = viewName;
        this.body = body;
    }

    public HttpResponse(String viewName) {
        this(
                new ResponseStatusLine(HttpVersion.HTTP_1_1, HttpStatusCode.OK_200),
                new HttpResponseHeader(new HashMap<>()),
                viewName,
                null,
                null
        );
    }

    public HttpResponse(HttpResponseBody body) {
        this(
                new ResponseStatusLine(HttpVersion.HTTP_1_1, HttpStatusCode.OK_200),
                new HttpResponseHeader(new HashMap<>()),
                null,
                body,
                null
        );
    }

    public static HttpResponse redirect(String viewName) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", viewName);
        return new HttpResponse(
        new ResponseStatusLine(HttpVersion.HTTP_1_1, HttpStatusCode.REDIRECTED),
                new HttpResponseHeader(headers),
                null,
                HttpResponseBody.EMPTY_RESPONSE_BODY,
                null
        );
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public boolean hasCookie() {
        return cookie != null;
    }

    public boolean isRedirect() {
        return statusLine.getStatusCode() == HttpStatusCode.REDIRECTED;
    }

    public void addHeader(String key, String value) {
        headers.add(key, value);
    }

    public boolean hasViewName() {
        return viewName != null;
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

    public String getViewName() {
        return viewName;
    }

    public Cookie getCookie() {
        return cookie;
    }
}
