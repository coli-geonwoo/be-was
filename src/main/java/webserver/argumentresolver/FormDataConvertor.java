package webserver.argumentresolver;

import http.ContentType;
import http.request.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import webserver.util.ClassScanUtils;

public class FormDataConvertor extends RequestBodyArgumentResolver {

    private static final int SPLIT_KEY_INDEX = 0;
    private static final int SPLIT_VALUE_INDEX = 1;
    private static final String TOKEN_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    @Override
    public boolean resolvableType(String contentType) {
        return ContentType.FORM_URLENCODED
                .getResponseContentType()
                .equals(contentType.toLowerCase());
    }

    @Override
    public Object resolve(HttpRequest request, Class<?> clazz) {
        byte[] rawBody = request.getRequestBody().getValue();
        String body = new String(rawBody, StandardCharsets.UTF_8);
        Map<String, String> dataMap = convertToMap(body);
        Object object = new ClassScanUtils<>().makeHandlerInstance(clazz);
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            setFieldValue(object, clazz, fieldName, value);
        }
        return object;
    }

    private Map<String, String> convertToMap(String rawValue) {
        String[] tokens = rawValue.split(TOKEN_DELIMITER);
        Map<String, String> parameters = new HashMap<>();
        for (String token : tokens) {
            String[] keyValue = token.split(KEY_VALUE_DELIMITER);
            parameters.put(keyValue[SPLIT_KEY_INDEX], keyValue[SPLIT_VALUE_INDEX]);
        }
        return parameters;
    }
}
