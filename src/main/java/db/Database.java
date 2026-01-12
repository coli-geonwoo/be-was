package db;

import java.util.Optional;
import application.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Database {

    private static final Logger log = LoggerFactory.getLogger(Database.class);

    private static Map<String, User> users = new HashMap<>();

    static {
        users.put("gw", new User("gw", "gw", "gw", "email@email.com"));
    }

    private Database() {}

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
        log.debug("User {} has been added", user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Optional<User> findByUserIdAndPassword(String userId, String password) {
        return users.values().stream()
                .filter(user -> user.getPassword().equals(password) && user.getUserId().equals(userId))
                .findAny();
    }

    public static Collection<User> findAll() {
        return users.values();
    }

    public static void clear() {
        users.clear();
    }
}
