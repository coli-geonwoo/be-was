package application.handler;

import application.config.argumentresolver.RepositoryConfig;
import application.repository.SessionRepository;
import application.repository.UserRepository;
import db.Database;
import db.SessionDataBase;
import application.dto.request.LoginRequest;
import application.exception.CustomException;
import application.exception.ErrorCode;
import http.HttpMethod;
import http.response.HttpResponse;
import http.response.ResponseCookie;
import java.util.Map;
import java.util.UUID;
import application.model.User;
import webserver.convertor.RequestBody;
import webserver.handler.HttpHandler;
import webserver.handler.RequestMapping;

@HttpHandler
public class LoginHandler {

    private final UserRepository userRepository = RepositoryConfig.userRepository();
    private final SessionRepository sessionRepository = RepositoryConfig.sessionRepository();

    @RequestMapping(method = HttpMethod.GET, path = "/login")
    public HttpResponse loginPage() {
        return new HttpResponse("/login/index.html");
    }

    @RequestMapping(method = HttpMethod.POST, path = "/login")
    public HttpResponse login(@RequestBody LoginRequest loginRequest) {
        User foundUser = userRepository.findByUserIdAndPassword(
                loginRequest.getUserId(),
                loginRequest.getPassword()
        ).orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));

        String sessionId = UUID.randomUUID().toString();
        saveSessionData(foundUser, sessionId);
        HttpResponse response = HttpResponse.redirect("/index.html");
        response.setCookie(new ResponseCookie(Map.of("sid", sessionId), "/", 3600));
        return response;
    }

    private void saveSessionData(User user, String sessionId) {
        String userId = user.getUserId();
        sessionRepository.saveData(sessionId, userId);
    }
}
