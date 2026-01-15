package application.exception;

import http.HttpStatusCode;

public enum ErrorCode {

    DUPLICATE_USER_ID(HttpStatusCode.BAD_REQUEST_400, "이미 존재하는 아이디입니다"),
    DUPLICATE_USER_NAME(HttpStatusCode.BAD_REQUEST_400, "이미 존재하는 닉네임입니다"),
    INVALID_USER_CREATE_INFO(HttpStatusCode.BAD_REQUEST_400, "유저의 닉네임, 아이디, 비밀번호는 4자 이상이어야 합니다"),
    REQUEST_SESSION_ID(HttpStatusCode.UNAUTHORIZED_401, "This access should be logged in"),
    LOGIN_FAILED(HttpStatusCode.UNAUTHORIZED_401, "비밀번호가 일치하지 않습니다"),
    AUTHENTICATION_FAILED(HttpStatusCode.UNAUTHORIZED_401, "Authentication failed"),
    USER_NOT_FOUND(HttpStatusCode.NOT_FOUND_404, "User not found"),
    ARTICLE_LIKES_NOT_FOUND(HttpStatusCode.NOT_FOUND_404, "ARTICLES LIKES NOT FOUND"),
    INVALID_ARTICLE_INPUT(HttpStatusCode.BAD_REQUEST_400, "Invalid article input"),
    INVALID_MULTIPART_FILE(HttpStatusCode.BAD_REQUEST_400, "Invalid multipart file"),
    INVALID_LATEST_ARTICLE_REQUEST(HttpStatusCode.BAD_REQUEST_400, "요청한 작성글 데이터가 없습니다."),

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
