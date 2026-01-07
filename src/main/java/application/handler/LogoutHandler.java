package application.handler;

import application.db.SessionDataBase;
import application.exception.CustomException;
import application.exception.ErrorCode;
import application.service.AuthService;
import http.request.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestCookie;
import http.response.ResponseCookie;
import http.response.HttpResponse;
import java.util.List;
import model.User;
import webserver.handler.AbstractHandler;
import webserver.handler.RequestMapping;

public class LogoutHandler extends AbstractHandler {

    private static List<String> HANDLING_PATHS = List.of("/logout");

    private final AuthService authService = new AuthService();

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        if(!request.hasCookie("sid")) {
            throw new CustomException(ErrorCode.REQUEST_SESSION_ID);
        }
        RequestCookie requestCookie = request.getRequestCookie();
        String sessionId = requestCookie.get("sid");
        User user = authService.authroize(sessionId);
        SessionDataBase.removeData(sessionId);
        HttpResponse response = HttpResponse.redirect("/index.html");
        response.setCookie(ResponseCookie.EXPIRED_RESPONSE_COOKIE);
        return response;
    }

    @RequestMapping(method = HttpMethod.POST, path = "/logout")
    public HttpResponse logOut(HttpRequest request) {
        if(!request.hasCookie("sid")) {
            throw new CustomException(ErrorCode.REQUEST_SESSION_ID);
        }
        RequestCookie requestCookie = request.getRequestCookie();
        String sessionId = requestCookie.get("sid");
        User user = authService.authroize(sessionId);
        SessionDataBase.removeData(sessionId);
        HttpResponse response = HttpResponse.redirect("/index.html");
        response.setCookie(ResponseCookie.EXPIRED_RESPONSE_COOKIE);
        return response;
    }
}
