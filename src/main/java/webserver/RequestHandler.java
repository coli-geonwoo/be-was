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
    private final HandlerMapper handlerMapper;
    private final ViewHandler viewHandler;

    public RequestHandler(
            Socket connection,
            HttpRequestParserFacade requestParserFacade,
            HttpResponseResolveFacade responseResolveFacade,
            HandlerMapper handlerMapper,
            ViewHandler viewHandler
    ) {
        this.connection = connection;
        this.requestParserFacade = requestParserFacade;
        this.responseResolveFacade = responseResolveFacade;
        this.handlerMapper = handlerMapper;
        this.viewHandler = viewHandler;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             OutputStream out = connection.getOutputStream()) {

            HttpRequest request = requestParserFacade.parse(br);
            HttpResponse response = handleByViewHandlerOrApplicationHandler(request);
            response = viewHandler.handleWithResponse(request, response);
            responseResolveFacade.resolve(request, response, new DataOutputStream(out));
            logger.debug("New Client Connect Response: {}", request.getRequestUrl());
        } catch (IOException e) {
            logger.error(e.getMessage());
            //TODO 500에러 페이지 반환
        }
    }

    private HttpResponse handleByViewHandlerOrApplicationHandler(HttpRequest request) {
        if (viewHandler.canHandle(request.getRequestUrl())) {
            return viewHandler.handleByFileName(request.getRequestUrl());
        }
        Handler mappedHandler = handlerMapper.mapByPath(request.getRequestUrl());
        return mappedHandler.handle(request);
    }
}
