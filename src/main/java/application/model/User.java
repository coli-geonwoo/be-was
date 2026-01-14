package application.model;

import application.exception.CustomException;
import application.exception.ErrorCode;

public class User {

    private static final int MIN_VALUE_LENGTH = 4;
    private static final String DEFAULT_IMAGE_URL = "https://avatars.githubusercontent.com/u/148152234?s=400&v=4";

    private String userId;
    private String password;
    private String name;
    private String email;
    private String imageUrl;

    public User(String userId, String password, String name, String email, String imageUrl) {
        validateLength(userId);
        validateLength(password);
        validateLength(name);
        validateNullable(email);
        validateNullable(imageUrl);
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public User(String userId, String password, String name, String email) {
        this(userId, password, name, email, DEFAULT_IMAGE_URL);
    }

    private void validateLength(String value) {
        validateNullable(value);
        if(value.length() < MIN_VALUE_LENGTH) {
            throw new CustomException(ErrorCode.INVALID_USER_CREATE_INFO);
        }
    }

    private void validateNullable(String value) {
        if(value == null || value.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_USER_CREATE_INFO);
        }
    }

    public boolean hasSamePassword(String password) {
        return this.password.equals(password);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
