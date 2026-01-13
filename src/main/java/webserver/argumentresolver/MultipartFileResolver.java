package webserver.argumentresolver;

import http.ContentType;
import java.util.List;

public class MultipartFileResolver extends RequestBodyArgumentResolver {

    private static final List<ContentType> HANDLING_TYPES = List.of(ContentType.JPG, ContentType.SVG, ContentType.PNG);

    @Override
    protected boolean resolvableType(String contentType) {
        String lowerCaseContentType = contentType.toLowerCase();
        return HANDLING_TYPES.stream()
                .anyMatch(handlingType -> handlingType.getResponseContentType().equals(lowerCaseContentType));
    }

    @Override
    protected Object resolveBody(String value, Class<?> clazz) {
        return new MultipartFile(value.getBytes());
    }
}
