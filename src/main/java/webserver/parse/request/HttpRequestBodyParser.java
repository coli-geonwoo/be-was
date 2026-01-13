package webserver.parse.request;

import http.request.HttpRequestBody;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

class HttpRequestBodyParser implements HttpRequestParser<HttpRequestBody> {

    @Override
    public HttpRequestBody parse(String input) {
        return new HttpRequestBody(input.getBytes(StandardCharsets.UTF_8));
    }

    public HttpRequestBody parseBody(InputStream inputStream, int contentLength) throws IOException {
        byte[] body = new byte[0];
        if (contentLength > 0) {
            body = new byte[contentLength];
            int totalRead = 0;

            while (totalRead < contentLength) {
                int read = inputStream.read(body, totalRead, contentLength - totalRead);
                if (read == -1) {
                    break;
                }
                totalRead += read;
            }
        }
        return new HttpRequestBody(body);
    }
}
