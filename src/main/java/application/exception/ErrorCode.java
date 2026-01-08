package application.exception;

import http.HttpStatusCode;

public enum ErrorCode {

    REQUEST_SESSION_ID(HttpStatusCode.UNAUTHORIZED_401, "This access should be logged in"),
    LOGIN_FAILED(HttpStatusCode.UNAUTHORIZED_401, "Login failed"),
    AUTHENTICATION_FAILED(HttpStatusCode.UNAUTHORIZED_401, "Authentication failed"),
    USER_NOT_FOUND(HttpStatusCode.NOT_FOUND_404, "User not found"),

    INTERNAL_SERVER_ERROR(HttpStatusCode.INTERNAL_SERVER_ERROR_500, "Internal Server Error"),
    ;

    private final HttpStatusCode code;
    private final String message;

    ErrorCode(HttpStatusCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatusCode getCode() {
        return code;
    }
}
