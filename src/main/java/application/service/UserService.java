package application.service;

import application.config.RepositoryConfig;
import application.dto.request.UserCreateRequest;
import application.exception.CustomException;
import application.exception.ErrorCode;
import application.model.User;
import application.repository.UserRepository;
import webserver.argumentresolver.MultipartFiles;

public class UserService {

    private final UserRepository userRepository = RepositoryConfig.userRepository();

    public User save(UserCreateRequest userCreateRequest) {
        validateDuplicateName(userCreateRequest.getName());
        validateDuplicateId(userCreateRequest.getUserId());
        User user = new User(
                userCreateRequest.getUserId(),
                userCreateRequest.getPassword(),
                userCreateRequest.getName(),
                userCreateRequest.getEmail()
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

    public User update(MultipartFiles multipartFiles) {
        return null;
    }
}
