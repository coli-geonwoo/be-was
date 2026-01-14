package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.lang.reflect.InvocationTargetException;
import webserver.exception.ExceptionHandlerRegistry;
import webserver.handler.HandlerExecution;
import webserver.handler.HandlerMapper;
import webserver.handler.ViewHandler;

public class HttpServlet {

    private final ViewHandler viewHandler;
    private final HandlerMapper handlerMapper;
    private final ExceptionHandlerRegistry exceptionHandlerRegistry;

    public HttpServlet(
            ViewHandler viewHandler,
            HandlerMapper handlerMapper,
            ExceptionHandlerRegistry exceptionHandlerRegistry
    ) {
        this.viewHandler = viewHandler;
        this.handlerMapper = handlerMapper;
        this.exceptionHandlerRegistry = exceptionHandlerRegistry;
    }

    public HttpResponse doDispatch(HttpRequest request) {
        try {
            HttpResponse response = handleByViewHandlerOrApplicationHandler(request);
            return viewHandler.handleWithResponse(response);
        } catch (Exception e) {
            HttpResponse exceptionHandledResponse = handleException(e);
            return viewHandler.handleWithResponse(exceptionHandledResponse);
        }
    }

    private HttpResponse handleException(Exception exception) {
        Exception handleException = exception;
        //method invoke로 가져오므로 getCause
        if (exception instanceof InvocationTargetException) {
            handleException = (Exception) exception.getCause();
        }
        return exceptionHandlerRegistry.handleByExceptionHandler(handleException);
    }

    private HttpResponse handleByViewHandlerOrApplicationHandler(HttpRequest request) throws InvocationTargetException {
        if (viewHandler.canHandle(request.getRequestUrl())) {
            return viewHandler.handleByFileName(request.getRequestUrl());
        }
        HandlerExecution handlerExecution = handlerMapper.mapByPath(request.getRequestMethod(), request.getRequestUrl());
        return handlerExecution.invoke(request);
    }
}
