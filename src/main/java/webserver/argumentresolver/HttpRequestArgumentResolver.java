package webserver.argumentresolver;

import http.request.HttpRequest;
import java.lang.reflect.Parameter;

public class HttpRequestArgumentResolver implements ArgumentResolver {

    @Override
    public boolean canConvert(HttpRequest request, Parameter parameter) {
        return parameter.getType().equals(HttpRequest.class);
    }

    @Override
    public Object resolve(HttpRequest request, Class<?> clazz) {
        return request;
    }
}
