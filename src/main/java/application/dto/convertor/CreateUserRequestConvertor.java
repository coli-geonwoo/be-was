package application.dto.convertor;

import application.dto.request.CreateUserRequest;
import java.util.HashMap;
import java.util.Map;

public class CreateUserRequestConvertor implements DtoConvertor<CreateUserRequest> {

    private static final int SPLIT_KEY_INDEX = 0;
    private static final int SPLIT_VALUE_INDEX = 1;
    private static final String TOKEN_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";
    private static final String USER_ID_KEY = "userId";
    private static final String PASSWORD_KEY = "password";
    private static final String EMAIL_KEY = "email";
    private static final String NAME_KEY = "name";

    @Override
    public CreateUserRequest convert(String rawValue) {
        String[] tokens = rawValue.split(TOKEN_DELIMITER);
        Map<String, String> parameters = new HashMap<>();
        for (String token : tokens) {
            String[] keyValue = token.split(KEY_VALUE_DELIMITER);
            parameters.put(keyValue[SPLIT_KEY_INDEX], keyValue[SPLIT_VALUE_INDEX]);
        }

        return new CreateUserRequest(
                getNonNullValue(USER_ID_KEY, parameters),
                getNonNullValue(PASSWORD_KEY, parameters),
                getNonNullValue(NAME_KEY, parameters),
                getNonNullValue(EMAIL_KEY, parameters)
        );
    }

    private String getNonNullValue(String key, Map<String, String> parameters) {
        String value = parameters.get(key);
        if (value == null) {
            throw new RuntimeException("Missing required parameter: " + key);
        }
        return value;
    }
}
