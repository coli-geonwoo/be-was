package webserver.resolver;

import http.request.HttpVersion;
import http.response.HttpStatusCode;
import http.response.ResponseStatusLine;
import java.util.StringJoiner;

class ResponseStatusLineResolver implements HttpResponseResolver<ResponseStatusLine> {

    private static final String HTTP_STATUS_LINE_DELIMITER = " ";

    @Override
    public String resolve(ResponseStatusLine statusLine) {
        StringJoiner stringJoiner = new StringJoiner(HTTP_STATUS_LINE_DELIMITER);
        HttpVersion httpVersion = statusLine.getHttpVersion();
        HttpStatusCode httpStatusCode = statusLine.getStatusCode();
        stringJoiner.add(HttpVersion.HTTP_VERSION_PREFIX + httpVersion.getVersion());
        stringJoiner.add(String.valueOf(httpStatusCode.getCode()));
        stringJoiner.add(httpStatusCode.getMessage());
        return stringJoiner.toString();
    }
}
