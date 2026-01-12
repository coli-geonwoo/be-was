package webserver.exception;

public class ResourceNotFoundException extends RuntimeException {

    private final String path;

    public ResourceNotFoundException(String path) {
        super("Can't find Resource " + path);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
