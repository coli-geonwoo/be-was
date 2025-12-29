package webserver.http.request;

public class HttpRequestBody {

    public static HttpRequestBody EMPTY_REQUEST_BODY = new HttpRequestBody("");

    private final String value;

    public HttpRequestBody(String value) {
        this.value = value;
    }
}
