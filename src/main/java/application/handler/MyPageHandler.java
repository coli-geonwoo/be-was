package application.handler;

import application.service.AuthService;
import http.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestCookie;
import http.response.HttpResponse;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class MyPageHandler {

    private final AuthService authService = new AuthService();

    @RequestMapping(method = HttpMethod.GET, path = "/mypage")
    public HttpResponse myPage(HttpRequest request) {
        if (request.hasCookie("sid")) {
            RequestCookie requestCookie = request.getRequestCookie();
            String sessionId = requestCookie.get("sid");
            authService.authroize(sessionId);
            return new HttpResponse("/mypage/index.html");
        }
        return HttpResponse.redirect("/");
    }
}
