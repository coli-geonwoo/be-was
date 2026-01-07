package webserver.parse.request;

import http.request.RequestCookie;
import java.io.BufferedReader;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpRequestHeader;
import http.request.HttpRequestLine;

public class HttpRequestParserFacade {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParserFacade.class);

    private static final int REQUEST_LINE_INDEX = 0;
    private static final String REQUEST_LINE_DELIMITER = "\r\n";
    private static final String REQUEST_HEADER_DELIMITER = "\r\n\r\n";

    private final HttpRequestParser<HttpRequestLine> httpRequestLineParser;
    private final HttpRequestParser<HttpRequestHeader> httpRequestHeaderParser;
    private final HttpRequestParser<HttpRequestBody> httpRequestBodyParser;
    private final HttpRequestParser<RequestCookie> cookieParser;

    public HttpRequestParserFacade() {
        this.httpRequestLineParser = new HttpRequestLineParser(new RequestUrlParser());
        this.httpRequestHeaderParser = new HttpRequestHeaderParser();
        this.httpRequestBodyParser = new HttpRequestBodyParser();
        this.cookieParser = new RequestCookieParser();
    }

    public HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        String rawRequest = getRawHttpRequest(bufferedReader);

        int firstLineEnd = rawRequest.indexOf(REQUEST_LINE_DELIMITER);
        int headerEnd = rawRequest.indexOf(REQUEST_HEADER_DELIMITER);
        String[] lines = rawRequest.split(REQUEST_LINE_DELIMITER);

        String rawRequestLine = lines[REQUEST_LINE_INDEX];
        HttpRequestLine requestLine = httpRequestLineParser.parse(rawRequestLine);
        logger.debug("Request Line - {}", rawRequestLine);

        String headerPart = parseRawHeaderPart(rawRequest, firstLineEnd, headerEnd);
        HttpRequestHeader requestHeader = httpRequestHeaderParser.parse(headerPart);
        logger.debug("Request Header : {}", headerPart);

        RequestCookie requestCookie = RequestCookie.EMPTY_COOKIE;
        if(requestHeader.containsHeader("Cookie")) {
            String rawCookie = requestHeader.getHeaderContent("Cookie");
            requestCookie = cookieParser.parse(rawCookie);
            logger.debug("Request Cookie - {}", rawCookie);
        }

        if(requestLine.getMethod() == HttpMethod.POST) {
            int contentLength = Integer.parseInt(requestHeader.getHeaderContent("Content-Length"));
            String rawBodyPart = parseRawBodyPart(rawRequest, headerEnd, contentLength);
            logger.debug("Request body : {}", rawBodyPart);
            HttpRequestBody requestBody = httpRequestBodyParser.parse(rawBodyPart);
            return new HttpRequest(requestLine, requestHeader, requestBody, requestCookie);
        }
        return new HttpRequest(requestLine, requestHeader, requestCookie);
    }

    private String getRawHttpRequest(BufferedReader br) throws IOException {
        StringBuilder rawRequest = new StringBuilder();
        String line;
        int contentLength = 0;

        while ((line = br.readLine()) != null) {
            rawRequest.append(line).append("\r\n");
            if (line.isEmpty()) {
                break;
            }

            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.substring("Content-Length:".length()).trim());
            }
        }

        if (contentLength > 0) {
            char[] bodyBuffer = new char[contentLength];
            int totalRead = 0;

            while (totalRead < contentLength) {
                int read = br.read(bodyBuffer, totalRead, contentLength - totalRead);;
                if (read == -1) {
                    break;
                }
                totalRead += read;
            }
            rawRequest.append(new String(bodyBuffer, 0, totalRead));
        }
        return rawRequest.toString();
    }

    private String parseRawHeaderPart(String rawRequest, int firstLineEnd, int headerEnd) {
        return rawRequest.substring(
                firstLineEnd + 2,
                headerEnd
        );
    }

    private String parseRawBodyPart(String rawRequest, int headerEnd, int contentLength) {
        int startIndex = headerEnd + REQUEST_HEADER_DELIMITER.length();
        return rawRequest.substring(startIndex, startIndex + contentLength);
    }
}
