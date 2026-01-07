package application.handler;

import application.db.Database;
import application.db.SessionDataBase;
import application.dto.request.LoginRequest;
import application.exception.CustomException;
import application.exception.ErrorCode;
import http.HttpMethod;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.response.ResponseCookie;
import http.response.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import model.User;
import webserver.handler.AbstractHandler;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class LoginHandler extends AbstractHandler {

    private static List<String> HANDLING_PATHS = List.of("/login");

    @Override
    public boolean canHandle(String path) {
        return HANDLING_PATHS.contains(path);
    }

    @Override
    public HttpResponse doGet(HttpRequest request) {
        return new HttpResponse("/login/index.html");
    }

    @Override
    public HttpResponse doPost(HttpRequest request) {
        HttpRequestBody requestBody = request.getRequestBody();
        String rawValue = requestBody.getValue();
        LoginRequest loginRequest = LoginRequest.fromFormRequest(rawValue);

        Optional<User> foundUser = Database.findByUserIdAndPassword(
                loginRequest.userId(),
                loginRequest.password()
        );

        if(foundUser.isPresent()) {
            String sessionId = UUID.randomUUID().toString();
            saveSessionData(foundUser.get(), sessionId);
            HttpResponse response = HttpResponse.redirect("/index.html");
            response.setCookie(new ResponseCookie(Map.of("sid", sessionId), "/", 3600));
            return response;
        }
        throw new CustomException(ErrorCode.LOGIN_FAILED);
    }

    @RequestMapping(method = HttpMethod.GET, path = "login")
    public HttpResponse loginPage(HttpRequest request) {
        return new HttpResponse("/login/index.html");
    }

    @RequestMapping(method = HttpMethod.POST, path = "login")
    public HttpResponse login(HttpRequest request) {
        HttpRequestBody requestBody = request.getRequestBody();
        String rawValue = requestBody.getValue();
        LoginRequest loginRequest = LoginRequest.fromFormRequest(rawValue);

        User foundUser = Database.findByUserIdAndPassword(
                loginRequest.userId(),
                loginRequest.password()
        ).orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));

        String sessionId = UUID.randomUUID().toString();
        saveSessionData(foundUser, sessionId);
        HttpResponse response = HttpResponse.redirect("/index.html");
        response.setCookie(new ResponseCookie(Map.of("sid", sessionId), "/", 3600));
        return response;
    }

    private void saveSessionData(User user, String sessionId) {
        String userId = user.getUserId();
        SessionDataBase.saveData(sessionId, userId);
    }
}
