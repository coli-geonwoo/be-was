package webserver.view;


import http.response.ModelAttributes;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import webserver.exception.ViewNotFoundException;

public class ViewResolver {

    private static final String DEFAULT_STATIC_FILE_PATH = "static";
    private static final String ATTRIBUTES_PREFIX = "{{";
    private static final String ATTRIBUTES_SUFFIX = "}}";

    public View resolveStaticFileByName(String fileName) {
        try (InputStream inputStream = findByPath(DEFAULT_STATIC_FILE_PATH + fileName)) {
            if (inputStream == null) {
                throw new ViewNotFoundException(fileName);
            }
            return new View(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public View resolveStaticFileWithModelAttributes(String fileName, ModelAttributes attributes) {
        View view = resolveStaticFileByName(fileName);
        String rawFile = new String(view.getContent());
        return new View(replaceAttributes(attributes, rawFile).getBytes());
    }

    private String replaceAttributes(ModelAttributes modelAttributes, String files) {
        StringBuilder builder = new StringBuilder(files);
        Map<String, String> attributes = modelAttributes.getAttributes();
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String key = ATTRIBUTES_PREFIX + entry.getKey() + ATTRIBUTES_SUFFIX;
            String value = entry.getValue();
            int startIndex = builder.indexOf(key);
            builder.replace(startIndex, startIndex + key.length(), value);
        }
        return builder.toString();
    }

    private InputStream findByPath(String path) {
        return ViewResolver.class
                .getClassLoader()
                .getResourceAsStream(path);
    }
}
