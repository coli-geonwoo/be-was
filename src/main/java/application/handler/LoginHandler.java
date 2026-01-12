package application.handler;

import application.dto.request.LoginRequest;
import application.service.AuthService;
import http.HttpMethod;
import http.response.HttpResponse;
import http.response.ResponseCookie;
import java.util.Map;
import webserver.convertor.RequestBody;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class LoginHandler {

    private final AuthService authService = new AuthService();

    @RequestMapping(method = HttpMethod.GET, path = "/login")
    public HttpResponse loginPage() {
        return new HttpResponse("/login/index.html");
    }

    @RequestMapping(method = HttpMethod.POST, path = "/login")
    public HttpResponse login(@RequestBody LoginRequest loginRequest) {
        String sessionId = authService.login(loginRequest);
        HttpResponse response = HttpResponse.redirect("/index.html");
        response.setCookie(new ResponseCookie(Map.of("sid", sessionId), "/", 3600));
        return response;
    }
}
