package webserver.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import webserver.view.View;
import webserver.view.ViewResolver;

public class ViewHandler implements Handler {

    private final ViewResolver viewResolver;

    public ViewHandler(ViewResolver viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Override
    public boolean canHandle(String path) {
        return true;
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        return handleByFileName(request.getRequestUrl());
    }

    public HttpResponse handleByFileName(String fileName) {
        View view = viewResolver.resolveStaticFileByName(fileName);
        HttpResponseBody body = new HttpResponseBody(view.getContent());
        return new HttpResponse(body);
    }
}
