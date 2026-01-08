package http;

public enum HttpStatusCode {

    OK_200(200, "OK"),
    CREATED_201(201, "Created"),
    REDIRECTED(302, "Found"),
    NOT_FOUND_404(404, "Not Found"),
    BAD_REQUEST_400(400, "Bad Request"),
    UNAUTHORIZED_401(401, "Unauthorized"),
    INTERNAL_SERVER_ERROR_500(500, "Internal Server Error"),
    ;

    private final int code;
    private final String message;

    HttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
