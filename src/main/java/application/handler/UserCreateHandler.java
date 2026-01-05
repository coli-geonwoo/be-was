package application.handler;

import application.db.Database;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import model.User;
import webserver.handler.AbstractHandler;
import webserver.handler.Handler;

public class UserCreateHandler extends AbstractHandler {

    private static final String HANDLING_PATHS = "/create";

    @Override
    public boolean canHandle(String path) {
        return path.startsWith(HANDLING_PATHS);
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        String userId = request.getRequestParameter("userId");
        String password = request.getRequestParameter("password");
        String email = request.getRequestParameter("email");
        String name = request.getRequestParameter("name");
        User user = new User(userId, password, name, email);
        Database.addUser(user);
        return new HttpResponse(HttpResponseBody.EMPTY_RESPONSE_BODY); //201 수정 고민
    }
}
