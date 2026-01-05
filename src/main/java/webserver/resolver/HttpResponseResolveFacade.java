package webserver.resolver;

import http.request.HttpRequest;
import http.request.RequestUrl;
import http.response.ContentType;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import http.response.HttpResponseHeader;
import http.response.ResponseStatusLine;
import java.io.DataOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseResolveFacade {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseResolveFacade.class);
    private static final String HTTP_RESPONSE_DELIMITER = "\r\n";

    private final HttpResponseResolver<ResponseStatusLine> statusLineResolver;
    private final HttpResponseResolver<HttpResponseHeader> responseHeaderResolver;

    public HttpResponseResolveFacade() {
        this.statusLineResolver = new ResponseStatusLineResolver();
        this.responseHeaderResolver = new HttpResponseHeaderResolver();
    }

    public void resolve(HttpRequest request, HttpResponse response, DataOutputStream dataOutputStream) {
        String statusLine = statusLineResolver.resolve(response.getStatusLine());
        logger.debug("statusLine: {}", statusLine);

        byte[] body = response.getBody();
        addHeaders(response, request.getRequestUrl());
        String headers = responseHeaderResolver.resolve(response.getHeaders());
        logger.debug("Response Header: {}", headers);

        try {
            dataOutputStream.writeBytes(statusLine + HTTP_RESPONSE_DELIMITER);
            dataOutputStream.writeBytes(headers);
            dataOutputStream.writeBytes(HTTP_RESPONSE_DELIMITER);
            if (body != null && body.length > 0) {
                dataOutputStream.write(body, 0, body.length);
            }
            dataOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to resolve http response", e);
        }
    }

    private void addHeaders(HttpResponse response, String requestUrl) {
        if (response.isRedirect()) {
            return;
        }
        byte[] body = response.getBody();
        response.addHeader(ContentType.CONTENT_TYPE_HEADER_KEY, ContentType.mapToType(requestUrl));
        response.addHeader("Content-Length", String.valueOf(body.length));
    }
}
