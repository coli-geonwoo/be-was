package webserver.view;


import http.response.ModelAttributes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import webserver.exception.ResourceNotFoundException;

public class ViewResolver {

    private static final String DEFAULT_STATIC_FILE_PATH = "static/";
    private static final String UPLOADED_STATIC_FILE_PATH = DEFAULT_STATIC_FILE_PATH + "uploads/";
    private static final String ABSOLUTE_STATIC_FILE_PATH = "src/main/resources/";
    private static final String ATTRIBUTES_PREFIX = "{{";
    private static final String ATTRIBUTES_SUFFIX = "}}";

    public View resolveStaticFileByName(String fileName) {
        String decodedPath = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        if (decodedPath.startsWith("/")) {
            decodedPath = decodedPath.substring(1);
        }
        try (InputStream inputStream = findByPath(DEFAULT_STATIC_FILE_PATH + decodedPath)) {
            if (inputStream == null) {
                throw new ResourceNotFoundException(decodedPath);
            }
            return new View(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new ResourceNotFoundException(fileName);
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
            while (builder.indexOf(key) != -1) {
                builder.replace(builder.indexOf(key), builder.indexOf(key) + key.length(), value);
            }
        }
        return builder.toString();
    }

    private InputStream findByPath(String path) throws FileNotFoundException {
        if (path.startsWith(UPLOADED_STATIC_FILE_PATH)) {
            File file = new File(ABSOLUTE_STATIC_FILE_PATH + path);
            if (file.exists()) {
                return new FileInputStream(file);
            }
        }
        return ViewResolver.class
                .getClassLoader()
                .getResourceAsStream(path);
    }
}
