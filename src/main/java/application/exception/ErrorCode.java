package application.exception;

import http.HttpStatusCode;

public enum ErrorCode {

    REQUEST_SESSION_ID(HttpStatusCode.UNAUTHORIZED_401, "This access should be logged in"),
    LOGIN_FAILED(HttpStatusCode.UNAUTHORIZED_401, "Login failed"),
    AUTHENTICATION_FAILED(HttpStatusCode.UNAUTHORIZED_401, "Authentication failed"),
    USER_NOT_FOUND(HttpStatusCode.NOT_FOUND_404, "User not found"),
    INVALID_ARTICLE_INPUT(HttpStatusCode.BAD_REQUEST_400, "Invalid article input"),
    INVALID_MULTIPART_FILE(HttpStatusCode.BAD_REQUEST_400, "Invalid multipart file"),

    INVALID_VIEW_NAME_ERROR(HttpStatusCode.INTERNAL_SERVER_ERROR_500, "Wrong View Name : View Name is Null Or Empty"),
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
