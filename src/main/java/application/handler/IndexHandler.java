package application.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.List;
import webserver.handler.Handler;

public class IndexHandler implements Handler {

    private static final List<String> HANDLING_PATHS = List.of("/", "/index", "/index.html");

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        return new HttpResponse("/index.html");
    }
}
