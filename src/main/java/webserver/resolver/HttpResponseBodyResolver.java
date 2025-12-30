package webserver.resolver;

import http.response.HttpResponseBody;

class HttpResponseBodyResolver implements HttpResponseResolver<HttpResponseBody> {

    @Override
    public String resolve(HttpResponseBody body) {
        return body.getBody();
    }
}
