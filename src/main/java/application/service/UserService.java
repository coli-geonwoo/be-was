package application.service;

import application.config.argumentresolver.RepositoryConfig;
import application.dto.request.CreateUserRequest;
import application.model.User;
import application.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository = RepositoryConfig.userRepository();

    public User save(CreateUserRequest createUserRequest) {
        User user = new User(
                createUserRequest.getUserId(),
                createUserRequest.getPassword(),
                createUserRequest.getName(),
                createUserRequest.getEmail()
        );
        return userRepository.save(user);
    }
}
