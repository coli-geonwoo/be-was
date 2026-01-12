package application.service;

import application.repository.SessionRepository;
import application.repository.UserRepository;
import db.Database;
import db.SessionDataBase;
import application.exception.CustomException;
import application.exception.ErrorCode;
import java.util.Optional;
import application.model.User;

public class AuthService {

    private final SessionRepository sessionRepository = new SessionDataBase();
    private final UserRepository userRepository = new Database();

    public User authroize(String sessionId) {
        Optional<String> foundUserId = sessionRepository.getData(sessionId);
        if (foundUserId.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return userRepository.findById(foundUserId.get());
    }
}
