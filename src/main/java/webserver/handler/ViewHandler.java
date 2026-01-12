package webserver.handler;

import http.request.HttpRequest;
import http.ContentType;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import http.response.ModelAttributes;
import webserver.view.View;
import webserver.view.ViewResolver;

public class ViewHandler implements Handler {

    private final ViewResolver viewResolver;

    public ViewHandler(ViewResolver viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Override
    public boolean canHandle(String path) {
        return ContentType.mapToType(path)
                .isPresent();
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        return handleByFileName(request.getRequestUrl());
    }

    public HttpResponse handleWithResponse(HttpResponse response) {
        if(response.hasViewName()) {
            return handleByFileNameAndModelAttributes(
                    response.getViewName(),
                    response.getModelAttributes()
            );
        }
        return response;
    }

    public HttpResponse handleByFileName(String fileName) {
        View view = viewResolver.resolveStaticFileByName(fileName);
        HttpResponseBody body = new HttpResponseBody(view.getContent());
        return new HttpResponse(body);
    }

    public HttpResponse handleByFileNameAndModelAttributes(String fileName, ModelAttributes modelAttributes) {
        View view = viewResolver.resolveStaticFileWithModelAttributes(fileName, modelAttributes);
        HttpResponseBody body = new HttpResponseBody(view.getContent());
        return new HttpResponse(body);
    }
}
