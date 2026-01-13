package http.request;

import java.nio.charset.StandardCharsets;

public class HttpRequestBody {

    public static HttpRequestBody EMPTY_REQUEST_BODY = new HttpRequestBody("".getBytes(StandardCharsets.UTF_8));

    private final byte[] value;

    public HttpRequestBody(byte[] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }
}
