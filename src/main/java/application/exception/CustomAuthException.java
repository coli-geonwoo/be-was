package application.exception;

public class CustomAuthException extends CustomException {

    public CustomAuthException() {
        super(ErrorCode.AUTHENTICATION_FAILED);
    }
}
