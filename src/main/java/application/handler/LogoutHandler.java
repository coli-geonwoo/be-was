package application.handler;

import application.db.SessionDataBase;
import application.exception.CustomException;
import application.exception.ErrorCode;
import application.service.AuthService;
import http.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestCookie;
import http.response.HttpResponse;
import http.response.ResponseCookie;
import model.User;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class LogoutHandler {

    private final AuthService authService = new AuthService();

    @RequestMapping(method = HttpMethod.POST, path = "/logout")
    public HttpResponse logOut(HttpRequest request) {
        if (!request.hasCookie("sid")) {
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
