package application.handler;

import application.service.AuthService;
import http.request.HttpRequest;
import http.request.RequestCookie;
import http.response.HttpResponse;
import http.response.ResponseCookie;
import java.util.List;
import model.User;
import webserver.handler.AbstractHandler;

public class IndexHandler extends AbstractHandler {

    private static final List<String> HANDLING_PATHS = List.of("/", "/index", "/index.html");

    private final AuthService authService = new AuthService();

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        try {
            if (request.hasCookie("sid")) {
                RequestCookie requestCookie = request.getRequestCookie();
                User user = authService.authroize(requestCookie.get("sid"));
                HttpResponse httpResponse = new HttpResponse("/main/index.html");
                httpResponse.addModelAttributes("account", user.getName());
                return httpResponse;
            }
            return new HttpResponse("/index.html");
        }catch(Exception exception) {
            //쿠키가 있는데 인증에 실패한 경우 > 자동 로그아웃
            HttpResponse response = new HttpResponse("/index.html");
            response.setCookie(ResponseCookie.EXPIRED_RESPONSE_COOKIE);
            return response;
        }
    }
}
