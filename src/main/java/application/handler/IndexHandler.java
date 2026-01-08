package application.handler;

import application.config.argumentresolver.AuthMember;
import application.service.AuthService;
import http.HttpMethod;
import http.request.HttpRequest;
import http.request.RequestCookie;
import http.response.HttpResponse;
import http.response.ResponseCookie;
import java.util.List;
import model.User;
import webserver.handler.AbstractHandler;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class IndexHandler {

    @RequestMapping(method = HttpMethod.GET, path = {"/", "/index.html", "/index"})
    public HttpResponse index(@AuthMember User user) {
        HttpResponse httpResponse = new HttpResponse("/main/index.html");
        httpResponse.addModelAttributes("account", user.getName());
        return httpResponse;
    }
}
