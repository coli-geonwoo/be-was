package webserver.argumentresolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import http.ContentType;
import http.request.HttpRequest;
import java.nio.charset.StandardCharsets;
import webserver.exception.RequestProcessingException;

public class JsonArgumentResolver extends RequestBodyArgumentResolver {

    @Override
    protected boolean resolvableType(String contentType) {
        return ContentType.JSON
                .getResponseContentType()
                .equals(contentType.toLowerCase());
    }

    @Override
    public Object resolve(HttpRequest request, Class<?> clazz) {
        byte[] rawBody = request.getRequestBody().getValue();
        String body = new String(rawBody, StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(body, clazz);
        } catch (JsonProcessingException e) {
            throw new RequestProcessingException("Error parsing JSON body" + e);
        }
    }
}
