package application.service;

import application.dto.request.LoginRequest;
import application.repository.SessionRepository;
import application.repository.UserRepository;
import db.memory.UserMemoryDatabase;
import db.memory.SessionMemoryDatabase;
import application.exception.CustomException;
import application.exception.ErrorCode;
import java.util.Optional;
import application.model.User;
import java.util.UUID;

public class AuthService {

    private final SessionRepository sessionRepository = new SessionMemoryDatabase();
    private final UserRepository userRepository = new UserMemoryDatabase();

    public User authroize(String sessionId) {
        Optional<String> foundUserId = sessionRepository.getData(sessionId);
        if (foundUserId.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return userRepository.findById(foundUserId.get());
    }

    public String login(LoginRequest loginRequest) {
        User foundUser = userRepository.findByUserIdAndPassword(
                loginRequest.getUserId(),
                loginRequest.getPassword()
        ).orElseThrow(() -> new CustomException(ErrorCode.LOGIN_FAILED));

        String sessionId = UUID.randomUUID().toString();
        saveSessionData(foundUser, sessionId);
        return sessionId;
    }

    private void saveSessionData(User user, String sessionId) {
        String userId = user.getUserId();
        sessionRepository.saveData(sessionId, userId);
    }

    public void logOut(String sessionId) {
        sessionRepository.removeData(sessionId);
    }
}
