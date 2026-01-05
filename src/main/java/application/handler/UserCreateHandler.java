package application.handler;

import application.db.Database;
import application.dto.convertor.CreateUserRequestConvertor;
import application.dto.request.CreateUserRequest;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.AbstractHandler;

public class UserCreateHandler extends AbstractHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserCreateHandler.class);

    private static final String HANDLING_PATHS = "/user/create";

    @Override
    public boolean canHandle(String path) {
        return path.startsWith(HANDLING_PATHS);
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        CreateUserRequestConvertor convertor = new CreateUserRequestConvertor();
        HttpRequestBody requestBody = request.getRequestBody();
        String rawValue = requestBody.getValue();
        CreateUserRequest createUserRequest = convertor.convert(rawValue);
        User user = new User(
                createUserRequest.userId(),
                createUserRequest.password(),
                createUserRequest.password(),
                createUserRequest.email()
        );
        Database.addUser(user);
        return HttpResponse.redirect("/index.html");
    }
}
