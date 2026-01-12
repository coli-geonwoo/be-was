package application.handler;

import application.config.argumentresolver.AuthMember;
import application.config.argumentresolver.RepositoryConfig;
import application.repository.SessionRepository;
import db.SessionDataBase;
import http.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestCookie;
import http.response.HttpResponse;
import http.response.ResponseCookie;
import application.model.User;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class LogoutHandler {

    private final SessionRepository sessionRepository = RepositoryConfig.sessionRepository();

    @RequestMapping(method = HttpMethod.POST, path = "/logout")
    public HttpResponse logOut(
            HttpRequest request,
            @AuthMember User user
    ) {
        RequestCookie requestCookie = request.getRequestCookie();
        String sessionId = requestCookie.get("sid");
        sessionRepository.removeData(sessionId);
        HttpResponse response = HttpResponse.redirect("/index.html");
        response.setCookie(ResponseCookie.EXPIRED_RESPONSE_COOKIE);
        return response;
    }
}
