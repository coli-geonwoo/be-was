package application.handler;

import http.HttpMethod;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.List;
import webserver.handler.AbstractHandler;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class RegisterHandler extends AbstractHandler {

    private static final List<String> HANDLING_PATHS = List.of("/registration");
    private static final String REGISTRATION_VIEW_FILE_PATH = "/registration/index.html";

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        return new HttpResponse(REGISTRATION_VIEW_FILE_PATH);
    }

    @RequestMapping(method = HttpMethod.GET, path = "/registration")
    public HttpResponse registration(HttpRequest request) {
        return new HttpResponse(REGISTRATION_VIEW_FILE_PATH);
    }

}
