package http.response;

public class HttpResponseBody {

    public static HttpResponseBody EMPTY_RESPONSE_BODY = new HttpResponseBody(new byte[0]);

    private final byte[] body;

    public HttpResponseBody(byte[] body) {
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }
}
