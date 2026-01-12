package application.handler;

import application.config.argumentresolver.RepositoryConfig;
import application.repository.UserRepository;
import db.Database;
import application.dto.request.CreateUserRequest;
import http.HttpMethod;
import http.response.HttpResponse;
import application.model.User;
import webserver.convertor.RequestBody;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class UserCreateHandler {

    private final UserRepository userRepository = RepositoryConfig.userRepository();

    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public HttpResponse save(@RequestBody CreateUserRequest createUserRequest) {
        User user = new User(
                createUserRequest.getUserId(),
                createUserRequest.getPassword(),
                createUserRequest.getName(),
                createUserRequest.getEmail()
        );
        userRepository.save(user);
        return HttpResponse.redirect("/index.html");
    }
}
