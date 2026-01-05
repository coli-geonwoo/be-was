package application.dto.convertor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FormRequestConvertor {

    private static final int SPLIT_KEY_INDEX = 0;
    private static final int SPLIT_VALUE_INDEX = 1;
    private static final String TOKEN_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    public Map<String, String> convert(String rawValue) {
        String[] tokens = rawValue.split(TOKEN_DELIMITER);
        Map<String, String> parameters = new HashMap<>();
        for (String token : tokens) {
            String[] keyValue = token.split(KEY_VALUE_DELIMITER);
            parameters.put(keyValue[SPLIT_KEY_INDEX], keyValue[SPLIT_VALUE_INDEX]);
        }
        return parameters;
    }

    private String getNonNullValue(String key, Map<String, String> parameters) {
        String value = parameters.get(key);
        if (value == null) {
            throw new RuntimeException("Missing required parameter: " + key);
        }
        return value;
    }
}
