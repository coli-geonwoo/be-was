package webserver.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import webserver.view.View;
import webserver.view.ViewResolver;

class DefaultViewHandler implements Handler {


    @Override
    public boolean canHandle(String path) {
        return true;
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        ViewResolver viewResolver = new ViewResolver();
        View view = viewResolver.resolveStaticFileByName(request.getRequestUrl());
        HttpResponseBody body = new HttpResponseBody(view.getContent());
        return new HttpResponse(body);
    }
}
