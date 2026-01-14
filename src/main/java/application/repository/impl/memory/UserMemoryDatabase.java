package application.repository.impl.memory;

import application.exception.CustomException;
import application.exception.ErrorCode;
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
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public Optional<User> findByName(String name) {
        return users.values().stream()
                .filter(user -> user.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<User> findByUserIdAndPassword(String userId, String password) {
        return users.values().stream()
                .filter(user -> user.getPassword().equals(password) && user.getUserId().equals(userId))
                .findAny();
    }

    @Override
    public User updateUserInfo(String userId, String nickname, String password, String imageUrl) {
        User foundUser = findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        User updatedUser = new User(
                foundUser.getUserId(),
                password == null ? foundUser.getPassword() : password,
                nickname == null ? foundUser.getName() : nickname,
                foundUser.getEmail(),
                imageUrl == null ? foundUser.getImageUrl() : imageUrl
        );
        users.put(updatedUser.getUserId(), updatedUser);
        return users.get(userId);
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
