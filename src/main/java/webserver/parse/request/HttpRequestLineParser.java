package webserver.parse.request;

import java.util.StringTokenizer;
import http.HttpMethod;
import http.request.HttpRequestLine;
import http.HttpVersion;
import http.request.RequestUrl;
import webserver.exception.RequestProcessingException;

class HttpRequestLineParser implements HttpRequestParser<HttpRequestLine> {

    private static final int HTTP_REQUEST_LINE_TOKEN_COUNT = 3;

    private final RequestUrlParser requestUrlParser;

    public HttpRequestLineParser(RequestUrlParser requestUrlParser) {
        this.requestUrlParser = requestUrlParser;
    }

    @Override
    public HttpRequestLine parse(String input) {
        StringTokenizer tokenizer = new StringTokenizer(input);
        validateRequestLine(tokenizer);
        HttpMethod method = HttpMethod.valueOf(tokenizer.nextToken().toUpperCase());
        RequestUrl requestUrl = requestUrlParser.parse(tokenizer.nextToken());
        HttpVersion httpVersion = HttpVersion.mapToHttpVersion(tokenizer.nextToken().toUpperCase());
        return new HttpRequestLine(method, requestUrl, httpVersion);
    }

    private void validateRequestLine(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() != HTTP_REQUEST_LINE_TOKEN_COUNT) {
            throw new RequestProcessingException("Invalid request line: " + tokenizer.countTokens());
        }
    }
}
