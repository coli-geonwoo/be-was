package application.handler;

import application.config.argumentresolver.AuthMember;
import http.HttpMethod;
import http.response.HttpResponse;
import application.model.User;
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
