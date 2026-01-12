package db;

import application.model.User;
import application.repository.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserMemoryDatabase implements UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserMemoryDatabase.class);

    private static Map<String, User> users = new ConcurrentHashMap<>();

    static {
        users.put("gw", new User("gw", "gw", "gw", "email@email.com"));
    }

    @Override
    public User save(User user) {
        users.put(user.getUserId(), user);
        log.debug("User {} has been added", user);
        return user;
    }

    @Override
    public User findById(String userId) {
        return users.get(userId);
    }

    @Override
    public Optional<User> findByUserIdAndPassword(String userId, String password) {
        return users.values().stream()
                .filter(user -> user.getPassword().equals(password) && user.getUserId().equals(userId))
                .findAny();
    }

    @Override
    public List<User> findAll() {
        return users.values().stream()
                .toList();
    }

    @Override
    public void clear() {
        users.clear();
    }
}
