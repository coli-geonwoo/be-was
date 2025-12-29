package webserver.view;


import java.util.Optional;

public class ViewResolver {

    public View resolveByPath(String path) {
        Optional<String> foundFile = findByPath(path);
        if (foundFile.isEmpty()) {
            throw new RuntimeException("No view found for path: " + path);
        }
        return new View(foundFile.get());
    }

    private Optional<String> findByPath(String path) {
        return Optional.ofNullable(
                getClass()
                .getClassLoader()
                .getResource(path)
                .getFile()
        );
    }
}
