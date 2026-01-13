package webserver.argumentresolver;

import http.ContentType;
import http.request.HttpRequest;
import http.request.HttpRequestBody;
import java.lang.reflect.Parameter;

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
        return resolveBody(requestBody.getValue(), clazz);
    }

    protected abstract boolean resolvableType(String contentType);

    protected abstract Object resolveBody(String value, Class<?> clazz);
}
