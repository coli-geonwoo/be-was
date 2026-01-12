package webserver.exception;

import http.HttpStatusCode;
import http.response.HttpResponse;
import http.response.ModelAttributes;
import java.util.Map;

public class DefaultExceptionHandler {

    @ExceptionHandler(value = ViewNotFoundException.class)
    public HttpResponse handle(ViewNotFoundException exception) {
        HttpResponse httpResponse = new HttpResponse("/error/error.html");
        httpResponse.addModelAttributes("status", String.valueOf(HttpStatusCode.NOT_FOUND_404));
        httpResponse.addModelAttributes("message", String.valueOf(exception.getMessage()));
        httpResponse.addModelAttributes("path", exception.getPath());
        return httpResponse;
    }
}
