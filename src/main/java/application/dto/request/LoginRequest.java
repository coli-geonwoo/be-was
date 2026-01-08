package application.dto.request;

public class LoginRequest {

    private String userId;
    private String password;

    public LoginRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    private LoginRequest() {}

    public String getPassword() {
        return password;
    }

    public String getUserId() {
        return userId;
    }
}
