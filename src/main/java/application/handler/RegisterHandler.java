package application.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import java.util.List;
import webserver.handler.Handler;
import webserver.view.View;
import webserver.view.ViewResolver;

public class RegisterHandler implements Handler {

    private static final List<String> HANDLING_PATHS = List.of("/registration");
    private static final String REGISTRATION_VIEW_FILE_PATH = "/registration/index.html";

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        ViewResolver viewResolver = new ViewResolver();
        View view = viewResolver.resolveStaticFileByName(REGISTRATION_VIEW_FILE_PATH);
        HttpResponseBody responseBody = new HttpResponseBody(view.getContent());
        return new HttpResponse(responseBody);
    }
}
