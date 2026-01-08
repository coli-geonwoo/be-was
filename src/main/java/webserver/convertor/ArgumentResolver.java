package webserver.convertor;

import http.request.HttpRequest;
import java.lang.reflect.Parameter;

public interface ArgumentResolver {

    boolean canConvert(HttpRequest request, Parameter parameter);

    Object resolve(HttpRequest request, Class<?> clazz);
}
