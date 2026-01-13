package application.repository;

import application.model.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(String id);

    Optional<User> findByName(String name);

    Optional<User> findByUserIdAndPassword(String userId, String password);

    List<User> findAll();

    void clear();
}
