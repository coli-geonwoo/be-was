package application.handler;

import application.dto.request.CreateUserRequest;
import application.service.UserService;
import http.HttpMethod;
import http.response.HttpResponse;
import webserver.argumentresolver.RequestBody;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class UserCreateHandler {

    private final UserService userService = new UserService();

    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public HttpResponse save(@RequestBody CreateUserRequest createUserRequest) {
        userService.save(createUserRequest);
        return HttpResponse.redirect("/index.html");
    }
}
