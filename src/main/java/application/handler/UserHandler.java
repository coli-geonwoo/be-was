package application.handler;

import application.config.argumentresolver.AuthMember;
import application.dto.request.UserCreateRequest;
import application.dto.request.UserUpdateRequest;
import application.model.User;
import application.service.UserService;
import http.HttpMethod;
import http.response.HttpResponse;
import webserver.argumentresolver.MultipartFiles;
import webserver.argumentresolver.RequestBody;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class UserHandler {

    private final UserService userService = new UserService();

    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public HttpResponse save(@RequestBody UserCreateRequest userCreateRequest) {
        userService.save(userCreateRequest);
        return HttpResponse.redirect("/login");
    }

    @RequestMapping(method = HttpMethod.PATCH, path = "/user")
    public HttpResponse update(
            @AuthMember User user,
            @RequestBody MultipartFiles multipartFiles
    ) {
        userService.update(user, new UserUpdateRequest(multipartFiles));
        return HttpResponse.ok();
    }
}
