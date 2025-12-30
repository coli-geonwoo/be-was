package webserver.handler;

import com.sun.net.httpserver.HttpHandler;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import java.util.List;
import webserver.view.View;
import webserver.view.ViewResolver;

public class IndexHandler implements Handler {

    private static final List<String> HANDLING_PATHS = List.of("/", "/index", "/index.html");

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        ViewResolver viewResolver = new ViewResolver();
        View view = viewResolver.resolveStaticFileByName("/index.html");
        HttpResponseBody responseBody = new HttpResponseBody(view.getContent());
        return new HttpResponse(responseBody);
    }
}
