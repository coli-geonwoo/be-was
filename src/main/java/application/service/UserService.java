package application.service;

import application.config.RepositoryConfig;
import application.dto.request.CreateUserRequest;
import application.exception.CustomException;
import application.exception.ErrorCode;
import application.model.User;
import application.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository = RepositoryConfig.userRepository();

    public User save(CreateUserRequest createUserRequest) {
        validateDuplicateName(createUserRequest.getName());
        validateDuplicateId(createUserRequest.getUserId());
        User user = new User(
                createUserRequest.getUserId(),
                createUserRequest.getPassword(),
                createUserRequest.getName(),
                createUserRequest.getEmail()
        );
        return userRepository.save(user);
    }

    private void validateDuplicateName(String name) {
        if (userRepository.findByName(name).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_NAME);
        }
    }

    private void validateDuplicateId(String userId) {
        if (userRepository.findById(userId).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_USER_ID);
        }

    }
}
