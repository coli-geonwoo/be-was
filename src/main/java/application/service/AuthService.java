package application.service;

import application.db.Database;
import application.db.SessionDataBase;
import application.exception.CustomException;
import application.exception.ErrorCode;
import java.util.Optional;
import model.User;

public class AuthService {

    public User authroize(String sessionId) {
        Optional<String> foundUserId = SessionDataBase.getData(sessionId);
        if (foundUserId.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return Database.findUserById(foundUserId.get());
    }
}
