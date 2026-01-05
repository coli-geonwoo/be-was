package application.dto.request;

import application.dto.convertor.FormRequestConvertor;
import java.util.Map;

public record LoginRequest(
        String userId,
        String password
) {

    public static LoginRequest fromFormRequest(String rawValue) {
        FormRequestConvertor formRequestConvertor = new FormRequestConvertor();
        Map<String, String> parameters = formRequestConvertor.convert(rawValue);
        return new LoginRequest(
                parameters.get("userId"),
                parameters.get("password")
        );
    }
}
