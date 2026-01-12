package webserver.exception;

public class ViewNotFoundException extends RuntimeException {

    private final String path;

    public ViewNotFoundException(String path) {
        super("Can't find View");
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
