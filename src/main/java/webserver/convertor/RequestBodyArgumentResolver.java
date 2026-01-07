package webserver.convertor;

import http.ContentType;
import http.request.HttpRequest;
import java.lang.reflect.Parameter;

public abstract class RequestBodyArgumentResolver implements ArgumentResolver {

    public boolean canConvert(HttpRequest request, Parameter parameter) {
        String contentType = request.getRequestHeader()
                .getHeaderContent(ContentType.CONTENT_TYPE_HEADER_KEY);
        return parameter.isAnnotationPresent(RequestBody.class) && resolvableType(contentType);
    }

    abstract boolean resolvableType(String contentType);


}
