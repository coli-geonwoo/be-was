package webserver.parse.request;

import http.request.RequestCookie;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;

public class HttpRequestParserFacade {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParserFacade.class);

    private static final int REQUEST_LINE_INDEX = 0;
    private static final String REQUEST_LINE_DELIMITER = "\r\n";
    private static final String CONTENT_LENGTH_HEADER = "Content-Length".toLowerCase();
    private static final String COOKIE_HEADER_NAME = "Cookie".toLowerCase();

    private final HttpRequestParser<HttpRequestLine> httpRequestLineParser;
    private final HttpRequestParser<HttpRequestHeader> httpRequestHeaderParser;
    private final HttpRequestBodyParser httpRequestBodyParser;
    private final HttpRequestParser<RequestCookie> cookieParser;

    public HttpRequestParserFacade() {
        this.httpRequestLineParser = new HttpRequestLineParser(new RequestUrlParser());
        this.httpRequestHeaderParser = new HttpRequestHeaderParser();
        this.httpRequestBodyParser = new HttpRequestBodyParser();
        this.cookieParser = new RequestCookieParser();
    }

    public HttpRequest parse(InputStream inputStream) throws IOException {

        String rawHeaderPart = getRawHeaderPart(inputStream);
        String[] lines = rawHeaderPart.split(REQUEST_LINE_DELIMITER);

        String rawRequestLine = lines[REQUEST_LINE_INDEX];
        HttpRequestLine requestLine = httpRequestLineParser.parse(rawRequestLine);
        logger.debug("Request Line - {}", rawRequestLine);

        rawHeaderPart = rawHeaderPart.substring(rawHeaderPart.indexOf(REQUEST_LINE_DELIMITER) + REQUEST_LINE_DELIMITER.length());
        logger.debug("Request Header : {}", rawHeaderPart);
        HttpRequestHeader requestHeader = httpRequestHeaderParser.parse(rawHeaderPart);

        RequestCookie requestCookie = RequestCookie.EMPTY_COOKIE;
        if(requestHeader.containsHeader(COOKIE_HEADER_NAME)) {
            String rawCookie = requestHeader.getHeaderContent(COOKIE_HEADER_NAME);
            requestCookie = cookieParser.parse(rawCookie);
            logger.debug("Request Cookie - {}", rawCookie);
        }

        if(requestLine.getMethod() == HttpMethod.POST) {
            int contentLength = Integer.parseInt(requestHeader.getHeaderContent(CONTENT_LENGTH_HEADER));
            HttpRequestBody requestBody = httpRequestBodyParser.parseBody(inputStream, contentLength);
            logger.debug("Request body : {}", new String(requestBody.getValue(), StandardCharsets.UTF_8));
            return new HttpRequest(requestLine, requestHeader, requestBody, requestCookie);
        }
        return new HttpRequest(requestLine, requestHeader, requestCookie);
    }

    private String getRawHeaderPart(InputStream inputStream) throws IOException {
        StringBuilder headerBuilder = new StringBuilder();
        int consecutiveNewlines = 0;

        while (true) {
            int b = inputStream.read();
            if (b == -1) {
                break;
            }
            char c = (char) b;
            headerBuilder.append(c);
            if (c == '\r' || c == '\n') {
                consecutiveNewlines++;
                if (consecutiveNewlines == 4) {
                    break;
                }
            } else {
                consecutiveNewlines = 0;
            }
        }
        return headerBuilder.toString();
    }
}
