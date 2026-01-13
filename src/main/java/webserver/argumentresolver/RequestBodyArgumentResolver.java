package webserver.argumentresolver;

import http.ContentType;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import webserver.exception.ArgumentResolvingException;

public abstract class RequestBodyArgumentResolver implements ArgumentResolver {

    @Override
    public final boolean canConvert(HttpRequest request, Parameter parameter) {
        String contentType = request.getRequestHeader()
                .getHeaderContent(ContentType.CONTENT_TYPE_HEADER_KEY);
        return parameter.isAnnotationPresent(RequestBody.class) && resolvableType(contentType);
    }

    @Override
    public final Object resolve(HttpRequest request, Class<?> clazz) {
        HttpRequestBody requestBody = request.getRequestBody();
        return resolveBody(new String(requestBody.getValue(), StandardCharsets.UTF_8), clazz);
    }

    protected final void setFieldValue(Object object, Class<?> clazz, String fieldName, Object value) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new ArgumentResolvingException("Can't find field " + fieldName + " in class " + clazz.getName());
        }
    }

    protected abstract boolean resolvableType(String contentType);

    protected abstract Object resolveBody(String value, Class<?> clazz);
}
