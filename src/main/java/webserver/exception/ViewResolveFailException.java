package webserver.exception;

public class ViewResolveFailException extends RuntimeException {

    private final String fileName;

    public ViewResolveFailException(String fileName) {
        super("Fail to View Resolving");
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
