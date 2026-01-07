package webserver.convertor;

import http.request.HttpRequest;
import java.lang.reflect.Parameter;

public class HttpRequestArgumentResolver implements ArgumentResolver {

    @Override
    public boolean canConvert(HttpRequest request, Parameter parameter) {
        return parameter.getClass()
                .isAssignableFrom(HttpRequest.class);
    }

    @Override
    public Object resolve(HttpRequest request, Class<?> clazz) {
        return request;
    }
}
