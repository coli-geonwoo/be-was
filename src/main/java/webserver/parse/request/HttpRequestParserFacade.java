package webserver.parse.request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestBody;
import webserver.http.request.HttpRequestHeader;
import webserver.http.request.HttpRequestLine;

public class HttpRequestParserFacade {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestParserFacade.class);

    private static final int REQUEST_LINE_INDEX = 0;
    private static final String REQUEST_LINE_DELIMITER = "\r\n";
    private static final String REQUEST_HEADER_DELIMITER = "\r\n\r\n";

    private final HttpRequestParser<HttpRequestLine> httpRequestLineParser;
    private final HttpRequestParser<HttpRequestHeader> httpRequestHeaderParser;
    private final HttpRequestParser<HttpRequestBody> httpRequestBodyParser;

    public HttpRequestParserFacade() {
        this.httpRequestLineParser = new HttpRequestLineParser();
        this.httpRequestHeaderParser = new HttpRequestHeaderParser();
        this.httpRequestBodyParser = new HttpRequestBodyParser();
    }

    //TODO content-Length 만큼 더 읽기
    public HttpRequest parse(String rawRequest) {
        int firstLineEnd = rawRequest.indexOf(REQUEST_LINE_DELIMITER);
        int headerEnd = rawRequest.indexOf(REQUEST_HEADER_DELIMITER);
        String[] lines = rawRequest.split(REQUEST_LINE_DELIMITER);

        String rawRequestLine = lines[REQUEST_LINE_INDEX];
        HttpRequestLine requestLine = httpRequestLineParser.parse(rawRequestLine);
        logger.debug("Request Line - method {} url {}", requestLine.getMethod().name(), requestLine.getRequestUrl());

        String headerPart = parseRawHeaderPart(rawRequest, firstLineEnd, headerEnd);
        HttpRequestHeader requestHeader = httpRequestHeaderParser.parse(headerPart);
        logger.debug("Request Header : {}", headerPart);

        if(requestLine.getMethod() == HttpMethod.POST) {
            String rawBodyPart = parseRawBodyPart(rawRequest, headerEnd);
            logger.debug("Request Header : {}", rawBodyPart);
            HttpRequestBody requestBody = httpRequestBodyParser.parse(rawBodyPart);

            return new HttpRequest(requestLine, requestHeader, requestBody);
        }
        return new HttpRequest(requestLine, requestHeader);
    }

    private String parseRawHeaderPart(String rawRequest, int firstLineEnd, int headerEnd) {

        return rawRequest.substring(
                firstLineEnd + 2,
                headerEnd
        );
    }

    private String parseRawBodyPart(String rawRequest, int headerEnd) {
        return rawRequest.substring(headerEnd + REQUEST_HEADER_DELIMITER.length());
    }
}
