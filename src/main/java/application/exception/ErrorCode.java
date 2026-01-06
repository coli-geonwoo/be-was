package application.exception;

import http.response.HttpStatusCode;

public enum ErrorCode {

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
