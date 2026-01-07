package application.dto.request;

public class LoginRequest2 {

    private String userId;
    private String password;

    public LoginRequest2(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    private LoginRequest2() {}

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }
}
