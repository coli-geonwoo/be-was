package application.exception;

import http.HttpVersion;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import http.response.HttpResponseHeader;
import http.HttpStatusCode;
import http.response.ResponseCookie;
import http.response.ResponseStatusLine;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.ExceptionHandler;

public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = CustomAuthException.class)
    public HttpResponse handleCustomAuthException(CustomAuthException authException) {
        logger.error(authException.getMessage(), authException);
        HttpResponse response = HttpResponse.redirect(authException.getRedirectViewName());
        response.setCookie(ResponseCookie.EXPIRED_RESPONSE_COOKIE);
        return response;
    }

    @ExceptionHandler(value = CustomException.class)
    public HttpResponse handleCustomException(CustomException customException) {
        logger.error(customException.getMessage(), customException);
        ErrorCode errorCode = customException.getErrorCode();

        return new HttpResponse(
                new ResponseStatusLine(HttpVersion.HTTP_1_1, errorCode.getCode()),
                new HttpResponseHeader(new HashMap<>()),
                null,
                new HttpResponseBody(errorCode.getMessage().getBytes()),
                null
        );
    }

    @ExceptionHandler(value = Exception.class)
    public HttpResponse handleException(Exception exception) {
        logger.error(exception.getMessage(), exception);

        return new HttpResponse(
                new ResponseStatusLine(HttpVersion.HTTP_1_1, HttpStatusCode.INTERNAL_SERVER_ERROR_500),
                new HttpResponseHeader(Map.of()),
                null,
                new HttpResponseBody(exception.getMessage().getBytes()),
                null
        );
    }
}
