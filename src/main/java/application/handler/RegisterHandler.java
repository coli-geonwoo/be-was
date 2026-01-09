package application.handler;

import http.HttpMethod;
import http.response.HttpResponse;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class RegisterHandler {

    private static final String REGISTRATION_VIEW_FILE_PATH = "/registration/index.html";

    @RequestMapping(method = HttpMethod.GET, path = "/registration")
    public HttpResponse registration() {
        return new HttpResponse(REGISTRATION_VIEW_FILE_PATH);
    }
}
