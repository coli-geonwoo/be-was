package application.handler;

import application.db.Database;
import application.dto.request.CreateUserRequest;
import http.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.response.HttpResponse;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.AbstractHandler;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class UserCreateHandler extends AbstractHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateHandler.class);

    private static final String HANDLING_PATHS = "/user/create";

    @Override
    public boolean canHandle(String path) {
        return path.startsWith(HANDLING_PATHS);
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        HttpRequestBody requestBody = request.getRequestBody();
        String rawValue = requestBody.getValue();
        CreateUserRequest createUserRequest = CreateUserRequest.fromFormRequest(rawValue);
        User user = new User(
                createUserRequest.userId(),
                createUserRequest.password(),
                createUserRequest.name(),
                createUserRequest.email()
        );
        Database.addUser(user);
        return HttpResponse.redirect("/index.html");
    }

    @RequestMapping(method = HttpMethod.POST, path = "/user/create")
    public HttpResponse save(HttpRequest request) {
        HttpRequestBody requestBody = request.getRequestBody();
        String rawValue = requestBody.getValue();
        CreateUserRequest createUserRequest = CreateUserRequest.fromFormRequest(rawValue);
        User user = new User(
                createUserRequest.userId(),
                createUserRequest.password(),
                createUserRequest.name(),
                createUserRequest.email()
        );
        Database.addUser(user);
        return HttpResponse.redirect("/index.html");
    }
}
