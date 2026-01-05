package application.handler;

import http.request.HttpRequest;
import http.response.Cookie;
import http.response.HttpResponse;
import java.util.List;
import webserver.handler.AbstractHandler;

public class LogoutHandler extends AbstractHandler {

    private static List<String> HANDLING_PATHS = List.of("/logout");

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        HttpResponse response = HttpResponse.redirect("/index.html");
        response.setCookie(Cookie.EXPIRED_COOKIE);
        return response;
    }
}
