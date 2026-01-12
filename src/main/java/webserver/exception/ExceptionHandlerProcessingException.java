package webserver.exception;

public class ExceptionHandlerProcessingException extends RuntimeException {
    public ExceptionHandlerProcessingException(Exception exception) {
        super("Exception occurred while handling exception" + exception.getMessage(), exception);
    }
}
