package webserver.handler;

import http.request.HttpRequest;
import http.ContentType;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import webserver.view.View;
import webserver.view.ViewResolver;

public class ViewHandler implements Handler {

    private final ViewResolver viewResolver;

    public ViewHandler(ViewResolver viewResolver) {
        this.viewResolver = viewResolver;
    }

    @Override
    public boolean canHandle(String path) {
        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        return ContentType.mapToType(decodedPath.substring(1))
                .isPresent();
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        String decodedPath = URLDecoder.decode(request.getRequestUrl(), StandardCharsets.UTF_8);
        return handleByFileName(decodedPath.substring(1));
    }

    public HttpResponse handleWithResponse(HttpResponse response) {
        if(response.hasViewName()) {
            return handleByFileNameAndModelAttributes(response);
        }
        return response;
    }

    public HttpResponse handleByFileName(String fileName) {
        View view = viewResolver.resolveStaticFileByName(fileName);
        HttpResponseBody body = new HttpResponseBody(view.getContent());
        return new HttpResponse(body);
    }

    public HttpResponse handleByFileNameAndModelAttributes(HttpResponse response) {
        View view = viewResolver.resolveStaticFileWithModelAttributes(response.getViewName(), response.getModelAttributes());
        HttpResponseBody body = new HttpResponseBody(view.getContent());
        return new HttpResponse(
                response.getStatusLine(),
                response.getHeaders(),
                response.getViewName(),
                body,
                response.getCookie()
        );
    }
}
