package application.handler;

import application.db.Database;
import application.dto.request.CreateUserRequest;
import http.HttpMethod;
import http.response.HttpResponse;
import model.User;
import webserver.convertor.RequestBody;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class UserCreateHandler {

    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public HttpResponse save(@RequestBody CreateUserRequest createUserRequest) {
        User user = new User(
                createUserRequest.getUserId(),
                createUserRequest.getPassword(),
                createUserRequest.getName(),
                createUserRequest.getEmail()
        );
        Database.addUser(user);
        return HttpResponse.redirect("/index.html");
    }
}
