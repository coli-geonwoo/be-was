package application.handler;

import application.service.AuthService;
import http.request.HttpRequest;
import http.request.RequestCookie;
import http.response.HttpResponse;
import java.util.List;
import webserver.handler.AbstractHandler;

public class MyPageHandler extends AbstractHandler {

    private static List<String> HANDLING_PATHS = List.of("/mypage");

    private final AuthService authService = new AuthService();

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        if(request.hasCookie("sid")) {
            RequestCookie requestCookie = request.getRequestCookie();
            String sessionId = requestCookie.get("sid");
            authService.authroize(sessionId);
            return new HttpResponse("/mypage/index.html");
        }
        return HttpResponse.redirect("/");
    }
}
