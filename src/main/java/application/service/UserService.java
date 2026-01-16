package application.service;

import application.config.RepositoryConfig;
import application.dto.request.UserCreateRequest;
import application.dto.request.UserUpdateRequest;
import application.exception.CustomException;
import application.exception.ErrorCode;
import application.model.User;
import application.repository.UserRepository;
import application.util.FileUploader;
import webserver.argumentresolver.MultipartFile;

public class UserService {

    private final UserRepository userRepository = RepositoryConfig.userRepository();
    private final FileUploader fileUploader = new FileUploader();

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

    public User update(User user, UserUpdateRequest userUpdateRequest) {
        validateDuplicateName(userUpdateRequest.nickname());
        if (userUpdateRequest.image() != null) {
            MultipartFile multipartFile = userUpdateRequest.image();
            String imageUrl = fileUploader.imageUpload(
                    multipartFile.getInputStream(),
                    multipartFile.getOriginalFilename()
            );
            return userRepository.updateUserInfo(
                    user.getUserId(),
                    userUpdateRequest.nickname(),
                    userUpdateRequest.password(),
                    imageUrl
            );
        }
        return userRepository.updateUserInfo(
                user.getUserId(),
                userUpdateRequest.nickname(),
                userUpdateRequest.password(),
                getUpdateImageUrl(userUpdateRequest)
        );
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

    private String getUpdateImageUrl(UserUpdateRequest userUpdateRequest) {
        if(userUpdateRequest.deleteProfile()) {
            return User.DEFAULT_IMAGE_URL;
        }
        return null;
    }

    public User findById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
