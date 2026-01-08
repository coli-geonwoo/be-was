package webserver.convertor;

import http.ContentType;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import util.ClassScanUtils;

public class FormDataConvertor extends RequestBodyArgumentResolver {

    private static final int SPLIT_KEY_INDEX = 0;
    private static final int SPLIT_VALUE_INDEX = 1;
    private static final String TOKEN_DELIMITER = "&";
    private static final String KEY_VALUE_DELIMITER = "=";

    @Override
    public boolean resolvableType(String contentType) {
        return ContentType.FORM_URLENCODED
                .getResponseContentType()
                .equals(contentType);
    }

    @Override
    public Object resolveBody(String body, Class<?> clazz) {
        Map<String, String> dataMap = convertToMap(body);
        Object object = new ClassScanUtils<>().makeHandlerInstance(clazz);
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();
            setFieldValue(object, clazz, fieldName, value);
        }
        return object;
    }

    private void setFieldValue(Object object, Class<?> clazz, String fieldName, Object value) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new RuntimeException("Can't find field " + fieldName + " in class " + clazz.getName(), exception);
        }
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
