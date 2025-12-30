package webserver.view;


import java.io.IOException;
import java.io.InputStream;

public class ViewResolver {

    private static final String DEFAULT_STATIC_FILE_PATH = "static";

    public View resolveStaticFileByName(String fileName) {
        try {
            InputStream inputStream = findByPath(DEFAULT_STATIC_FILE_PATH + fileName);
            System.out.println("path : " + DEFAULT_STATIC_FILE_PATH + fileName);
            if (inputStream == null) {
                throw new RuntimeException("No view found for path: " + fileName);
            }
            return new View(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream findByPath(String path) {
        return ViewResolver.class
                .getClassLoader()
                .getResourceAsStream(path);
    }
}
