package webserver.exception;

public class RequestProcessingException extends RuntimeException {
    public RequestProcessingException(String message) {
        super(message);
    }
}
