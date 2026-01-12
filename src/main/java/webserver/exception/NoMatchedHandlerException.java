package webserver.exception;

public class NoMatchedHandlerException extends RuntimeException {

    public NoMatchedHandlerException(String message) {
        super(message);
    }
}
