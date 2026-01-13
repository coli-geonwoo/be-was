package db.h2;

import application.model.User;
import application.repository.UserRepository;
import java.util.List;
import java.util.Optional;

public class UserH2Database implements UserRepository {

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public Optional<User> findByUserIdAndPassword(String userId, String password) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public void clear() {

    }
}
