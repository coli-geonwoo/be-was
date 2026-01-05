package application.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.List;
import webserver.handler.AbstractHandler;

public class LoginHandler extends AbstractHandler {

    private static List<String> HANDLING_PATHS = List.of("/login");

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        return new HttpResponse("/login/index.html");
    }
}
