package webserver;

import http.response.HttpResponse;
import http.response.HttpResponseBody;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import http.request.HttpRequest;
import http.response.ContentType;
import webserver.handler.Handler;
import webserver.handler.HandlerMapper;
import webserver.handler.ViewHandler;
import webserver.parse.request.HttpRequestParserFacade;
import webserver.resolver.HttpResponseResolveFacade;
import webserver.view.View;
import webserver.view.ViewResolver;

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
            String rawRequest = getRawHttpRequest(br);

            if(rawRequest.isBlank()) {
               return;
            }
            HttpRequest request = requestParserFacade.parse(rawRequest);
            Handler mappedHandler = handlerMapper.mapByPath(request.getRequestUrl())
                    .orElse(viewHandler);
            HttpResponse response = handleRequest(request, mappedHandler);
            responseResolveFacade.resolve(request, response, new DataOutputStream(out));
            logger.debug("New Client Connect Response: {}", request.getRequestUrl());
        } catch (IOException e) {
            logger.error(e.getMessage());
            //TODO 500에러 페이지 반환
        }
    }

    private HttpResponse handleRequest(HttpRequest request, Handler handler) {
        HttpResponse response = handler.handle(request);
        if(response.hasViewName()) {
            return viewHandler.handleByFileName(response.getViewName());
        }
        return response;
    }

    private String getRawHttpRequest(BufferedReader br) throws IOException {
        StringBuilder rawRequest = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            rawRequest.append(line).append("\r\n");
            if(!br.ready()) {
                break;
            }
        }
        return rawRequest.toString();
    }
}
