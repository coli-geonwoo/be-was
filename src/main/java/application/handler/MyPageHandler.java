package application.handler;

import application.config.argumentresolver.AuthMember;
import http.HttpMethod;
import http.response.HttpResponse;
import application.model.User;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class MyPageHandler {

    @RequestMapping(method = HttpMethod.GET, path = "/mypage")
    public HttpResponse myPage(
            @AuthMember User user
    ) {
        HttpResponse response = new HttpResponse("/mypage/index.html");
        response.addModelAttributes("nickname", user.getName());
        response.addModelAttributes("profileImage", user.getImageUrl());
        return response;
    }
}
