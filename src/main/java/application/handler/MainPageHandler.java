package application.handler;

import application.config.argumentresolver.AuthMember;
import application.model.User;
import http.HttpMethod;
import http.response.HttpResponse;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class MainPageHandler {

    @RequestMapping(method = HttpMethod.GET, path = "/main")
    public HttpResponse mainPage(@AuthMember User user) {
        HttpResponse response = new HttpResponse("/main/index.html");
        response.addModelAttributes("account", user.getName());
        response.addModelAttributes("profileImage", user.getImageUrl());
        return response;
    }
}
