package application.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.List;
import webserver.handler.Handler;

public class RegisterHandler implements Handler {

    private static final List<String> HANDLING_PATHS = List.of("/registration");
    private static final String REGISTRATION_VIEW_FILE_PATH = "/registration/index.html";

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        return new HttpResponse(REGISTRATION_VIEW_FILE_PATH);
    }
}
