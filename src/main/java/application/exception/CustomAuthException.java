package application.exception;

public class CustomAuthException extends CustomException {

    private static final String DEFAULT_REDIRECT_VIEW_NAME = "/index.html";

    private final String redirectViewName;

    public CustomAuthException(String redirectViewName) {
        super(ErrorCode.AUTHENTICATION_FAILED);
        validate(redirectViewName);
        this.redirectViewName = redirectViewName;
    }

    public CustomAuthException() {
        this(DEFAULT_REDIRECT_VIEW_NAME);
    }

    private void validate(String redirectViewName) {
        if (redirectViewName == null || redirectViewName.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_VIEW_NAME_ERROR);
        }
    }

    public String getRedirectViewName() {
        return redirectViewName;
    }
}
