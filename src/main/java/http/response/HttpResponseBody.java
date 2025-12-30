package http.response;

public class HttpResponseBody {

    public static HttpResponseBody EMPTY_RESPONSE_BODY = new HttpResponseBody("");

    private final String body;

    public HttpResponseBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
