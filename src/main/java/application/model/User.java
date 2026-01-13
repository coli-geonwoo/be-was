package application.model;

import application.exception.CustomException;
import application.exception.ErrorCode;

public class User {

    private static final int MIN_VALUE_LENGTH = 4;

    private String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        validate(userId);
        validate(password);
        validate(name);
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    private void validate(String value) {
        if(value == null || value.isBlank() || value.length() < MIN_VALUE_LENGTH) {
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

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }
}
