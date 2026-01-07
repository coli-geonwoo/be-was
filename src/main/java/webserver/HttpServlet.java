package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.lang.reflect.Method;
import webserver.exception.ExceptionHandlerRegistry;
import webserver.handler.Handler;
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
        try{
            HttpResponse response = handleByViewHandlerOrApplicationHandler(request);
            return viewHandler.handleWithResponse(request, response);
        }catch (Exception e) {
            return exceptionHandlerRegistry.handleByExceptionHandler(e);
        }
    }

    //TODO method
    private HttpResponse handleByViewHandlerOrApplicationHandler(HttpRequest request) {
        if (viewHandler.canHandle(request.getRequestUrl())) {
            return viewHandler.handleByFileName(request.getRequestUrl());
        }
        HandlerExecution handlerExecution = handlerMapper.mapByPath(request.getRequestMethod(), request.getRequestUrl());
        return handlerExecution.invoke(request);
    }
}
