package application.service;

import application.db.Database;
import application.db.SessionDataBase;
import java.util.Optional;
import javassist.NotFoundException;
import model.User;

public class AuthService {


    public User authroize(String sessionId) {
        Optional<String> foundUserId = SessionDataBase.getData(sessionId);
        if (foundUserId.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return Database.findUserById(foundUserId.get());
    }
}
