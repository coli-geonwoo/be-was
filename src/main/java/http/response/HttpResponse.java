package http.response;

import http.HttpStatusCode;
import http.HttpVersion;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final ResponseStatusLine statusLine;
    private final HttpResponseHeader headers;
    private final String viewName;
    private final ModelAttributes modelAttributes;
    private final HttpResponseBody body;
    private ResponseCookie responseCookie;

    public HttpResponse(
            ResponseStatusLine statusLine,
            HttpResponseHeader headers,
            String viewName,
            HttpResponseBody body,
            ResponseCookie responseCookie
    ) {
        this.statusLine = statusLine;
        this.headers = headers;
        this.viewName = viewName;
        this.modelAttributes = new ModelAttributes(new HashMap<>());
        this.body = body;
        this.responseCookie = responseCookie;
    }

    public HttpResponse(String viewName) {
        this(
                new ResponseStatusLine(HttpVersion.HTTP_1_1, HttpStatusCode.OK_200),
                new HttpResponseHeader(new HashMap<>()),
                viewName,
                HttpResponseBody.EMPTY_RESPONSE_BODY,
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

    public static HttpResponse ok() {
        return new HttpResponse(HttpResponseBody.EMPTY_RESPONSE_BODY);
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

    public void setCookie(ResponseCookie responseCookie) {
        this.responseCookie = responseCookie;
    }

    public boolean hasCookie() {
        return responseCookie != null;
    }

    public void addModelAttributes(String key, String value) {
        modelAttributes.put(key, value);
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
        if(body == null) {
            return new byte[0];
        }
        return body.getBody();
    }

    public String getViewName() {
        return viewName;
    }

    public ResponseCookie getCookie() {
        return responseCookie;
    }

    public ModelAttributes getModelAttributes() {
        return modelAttributes;
    }
}
