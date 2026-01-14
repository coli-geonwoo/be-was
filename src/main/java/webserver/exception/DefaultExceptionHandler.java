package webserver.exception;

import http.HttpStatusCode;
import http.HttpVersion;
import http.response.HttpResponse;
import http.response.HttpResponseBody;
import http.response.HttpResponseHeader;
import http.response.ResponseCookie;
import http.response.ResponseStatusLine;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public HttpResponse handleResourceNotFoundException(ResourceNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return makeErrorResponse(exception.getClass(), HttpStatusCode.NOT_FOUND_404, exception.getMessage(), exception.getPath());
    }

    @ExceptionHandler(value = ViewResolveFailException.class)
    public HttpResponse handleViewResolveFailException(ViewResolveFailException exception) {
        logger.error(exception.getMessage(), exception);
        return makeErrorResponse(
                exception.getClass(),
                HttpStatusCode.INTERNAL_SERVER_ERROR_500,
                exception.getMessage(),
                exception.getFileName()
        );
    }

    @ExceptionHandler(value = RequestProcessingException.class)
    public HttpResponse handleRequestProcessingException(RequestProcessingException exception) {
        logger.error(exception.getMessage(), exception);
        return makeErrorResponse(
                exception.getClass(),
                HttpStatusCode.INTERNAL_SERVER_ERROR_500,
                exception.getMessage(),
                ""
        );
    }

    @ExceptionHandler(value = ArgumentResolvingException.class)
    public HttpResponse handleArgumentResolvingException(ArgumentResolvingException exception) {
        logger.error(exception.getMessage(), exception);
        return makeErrorResponse(
                exception.getClass(),
                HttpStatusCode.INTERNAL_SERVER_ERROR_500,
                exception.getMessage(),
                ""
        );
    }

    @ExceptionHandler(value = NoMatchedHandlerException.class)
    public HttpResponse handleNoMatchedException(NoMatchedHandlerException exception) {
        logger.error(exception.getMessage(), exception);
        return makeErrorResponse(
                exception.getClass(),
                HttpStatusCode.METHOD_NOT_ALLOWED,
                exception.getMessage(),
                ""
        );
    }

    @ExceptionHandler(value = ExceptionHandlerProcessingException.class)
    public HttpResponse handleExceptionHandlingProcessingException(ExceptionHandlerProcessingException exception) {
        logger.error(exception.getMessage(), exception);
        return makeErrorResponse(
                exception.getClass(),
                HttpStatusCode.INTERNAL_SERVER_ERROR_500,
                exception.getMessage(),
                ""
        );
    }

    @ExceptionHandler(value = Exception.class)
    public HttpResponse handleException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return makeErrorResponse(
                exception.getClass(),
                HttpStatusCode.INTERNAL_SERVER_ERROR_500,
                exception.getMessage(),
                ""
        );
    }

    private HttpResponse makeErrorResponse(
            Class<? extends Exception> exceptionType,
            HttpStatusCode httpStatusCode,
            String message,
            String path
    ) {
        HttpResponse httpResponse = new HttpResponse(
                new ResponseStatusLine(HttpVersion.HTTP_1_1, httpStatusCode),
                new HttpResponseHeader(new HashMap<>()),
                "/error/error.html",
                HttpResponseBody.EMPTY_RESPONSE_BODY,
                ResponseCookie.EXPIRED_RESPONSE_COOKIE
        );

        httpResponse.addModelAttributes("status", String.valueOf(httpStatusCode.getCode()));
        httpResponse.addModelAttributes("message", message);
        httpResponse.addModelAttributes("error", exceptionType.getSimpleName());
        httpResponse.addModelAttributes("path", path);
        return httpResponse;
    }
}
