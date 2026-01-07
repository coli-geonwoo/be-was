package webserver;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.ExceptionHandlerRegistry;
import webserver.handler.Handler;
import webserver.handler.HandlerMapper;
import webserver.handler.ViewHandler;
import webserver.parse.request.HttpRequestParserFacade;
import webserver.resolver.HttpResponseResolveFacade;

public class RequestHandler implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;
    private final HttpRequestParserFacade requestParserFacade;
    private final HttpResponseResolveFacade responseResolveFacade;
    private final HttpServlet httpServlet;

    public RequestHandler(
            Socket connection,
            HttpRequestParserFacade requestParserFacade,
            HttpResponseResolveFacade responseResolveFacade,
            HandlerMapper handlerMapper,
            ViewHandler viewHandler,
            ExceptionHandlerRegistry exceptionHandlerRegistry
    ) {
        this.connection = connection;
        this.requestParserFacade = requestParserFacade;
        this.responseResolveFacade = responseResolveFacade;
        this.httpServlet = new HttpServlet(viewHandler, handlerMapper, exceptionHandlerRegistry);
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             OutputStream out = connection.getOutputStream()) {
            HttpRequest request = requestParserFacade.parse(br);
            HttpResponse response = httpServlet.doDispatch(request);
            responseResolveFacade.resolve(request, response, new DataOutputStream(out));
            logger.debug("New Client Connect Response: {}", request.getRequestUrl());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Exception occurred while handling exception", e);
        }
    }
}
