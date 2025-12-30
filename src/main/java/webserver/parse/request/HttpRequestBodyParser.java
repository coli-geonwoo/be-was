package webserver.parse.request;

import http.request.HttpRequestBody;

class HttpRequestBodyParser implements HttpRequestParser<HttpRequestBody> {

    @Override
    public HttpRequestBody parse(String input) {
        return new HttpRequestBody(input);
    }
}
