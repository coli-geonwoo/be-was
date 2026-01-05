package application.dto.request;

public record CreateUserRequest(
        String userId,
        String password,
        String name,
        String email
) {

}
