package application.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.List;
import webserver.handler.AbstractHandler;

public class IndexHandler extends AbstractHandler {

    private static final List<String> HANDLING_PATHS = List.of("/", "/index", "/index.html");

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        return new HttpResponse("/index.html");
    }
}
