package application.dto.request;

import application.dto.convertor.FormRequestConvertor;
import java.util.Map;
import model.User;

public record CreateUserRequest(
        String userId,
        String password,
        String name,
        String email
) {

    public static CreateUserRequest fromFormRequest(String rawValue) {
        FormRequestConvertor formRequestConvertor = new FormRequestConvertor();
        Map<String, String> parameters = formRequestConvertor.convert(rawValue);
        return new CreateUserRequest(
                parameters.get("userId"),
                parameters.get("password"),
                parameters.get("name"),
                parameters.get("email")
        );
    }
}
