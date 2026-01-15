package webserver.resolver;

import http.request.HttpRequest;
import http.ContentType;
import http.response.ResponseCookie;
import http.response.HttpResponse;
import http.response.HttpResponseHeader;
import http.response.ResponseStatusLine;
import java.io.DataOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponseResolveFacade {

    private static final Logger logger = LoggerFactory.getLogger(HttpResponseResolveFacade.class);
    private static final String HTTP_RESPONSE_DELIMITER = "\r\n";
    private static final String HTTP_END_HEAD_PART_DELIMITER = "\r\n\r\n";
    private static final String CONTENT_LENGTH_HEADER = "Content-Length".toLowerCase();
    private static final String SET_COOKIE_HEADER = "Set-Cookie".toLowerCase();

    private final HttpResponseResolver<ResponseStatusLine> statusLineResolver;
    private final HttpResponseResolver<HttpResponseHeader> responseHeaderResolver;
    private final HttpResponseResolver<ResponseCookie> responseCookieResolver;

    public HttpResponseResolveFacade() {
        this.statusLineResolver = new ResponseStatusLineResolver();
        this.responseHeaderResolver = new HttpResponseHeaderResolver();
        this.responseCookieResolver = new HttpResponseCookieResolver();
    }

    public void resolve(HttpRequest request, HttpResponse response, DataOutputStream dataOutputStream) {
        String statusLine = statusLineResolver.resolve(response.getStatusLine());
        logger.debug("statusLine: {}", statusLine);

        byte[] body = response.getBody();
        addHeaders(response, request.getRequestUrl());
        setCookie(response);
        String headers = responseHeaderResolver.resolve(response.getHeaders());
        logger.debug("Response Header: {}", headers);

        try {
            dataOutputStream.writeBytes(statusLine + HTTP_RESPONSE_DELIMITER);
            dataOutputStream.writeBytes(headers);
            dataOutputStream.writeBytes(HTTP_END_HEAD_PART_DELIMITER);
            if (body != null && body.length > 0) {
                dataOutputStream.write(body, 0, body.length);
            }
            dataOutputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to resolve http response", e);
        }
    }

    private void addHeaders(HttpResponse response, String requestUrl) {
        String contentType;
        if(response.getContentType() != null) {
                contentType = response.getContentType()
                        .getResponseContentType();
        }else {
            contentType = ContentType.mapToType(requestUrl)
                    .orElse(ContentType.HTML)
                    .getResponseContentType();
        }
        byte[] body = response.getBody();
        response.addHeader(ContentType.CONTENT_TYPE_HEADER_KEY, contentType);
        response.addHeader(CONTENT_LENGTH_HEADER, String.valueOf(body.length));
    }

    private void setCookie(HttpResponse response) {
        if(response.hasCookie()) {
            String cookieContent = responseCookieResolver.resolve(response.getCookie());
            logger.debug("Cookie content: {}", cookieContent);
            response.addHeader(SET_COOKIE_HEADER, cookieContent);
        }
    }
}
