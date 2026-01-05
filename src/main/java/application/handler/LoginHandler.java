package application.handler;

import application.db.Database;
import application.db.SessionDataBase;
import application.dto.request.LoginRequest;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import http.request.HttpVersion;
import http.response.Cookie;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import http.response.HttpResponseHeader;
import http.response.HttpStatusCode;
import http.response.ResponseStatusLine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import model.User;
import webserver.handler.AbstractHandler;

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
            response.setCookie(new Cookie(Map.of("sid", sessionId), "/", 3600));
            return response;
        }
        //로그인 실패 시
        return new HttpResponse(
                new ResponseStatusLine(HttpVersion.HTTP_1_1, HttpStatusCode.UNAUTHORIZED_401),
                new HttpResponseHeader(new HashMap<>()),
                null,
                HttpResponseBody.EMPTY_RESPONSE_BODY,
                null
        );
    }

    private void saveSessionData(User user, String sessionId) {
        String userId = user.getUserId();
        SessionDataBase.saveData(sessionId, userId);
    }
}
