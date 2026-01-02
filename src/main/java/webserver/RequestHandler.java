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
    private final ViewResolver viewResolver;

    public RequestHandler(
            Socket connection,
            HttpRequestParserFacade requestParserFacade,
            HttpResponseResolveFacade responseResolveFacade,
            HandlerMapper handlerMapper,
            ViewResolver viewResolver
    ) {
        this.connection = connection;
        this.requestParserFacade = requestParserFacade;
        this.responseResolveFacade = responseResolveFacade;
        this.handlerMapper = handlerMapper;
        this.viewResolver = viewResolver;
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
            Optional<Handler> mappedHandler = handlerMapper.mapByPath(request.getRequestUrl());

            HttpResponse response;

            if(mappedHandler.isPresent()) {
                response = mappedHandler.get().handle(request);
                if(response.hasViewName()) {
                    response = resolveStaticFileResponse(response.getViewName());
                }
            } else {
                response = resolveStaticFileResponse(request.getRequestUrl());
            }

            responseResolveFacade.resolve(request, response, new DataOutputStream(out));
            logger.debug("New Client Connect Response: {}", request.getRequestUrl());

        } catch (IOException e) {
            logger.error(e.getMessage());
            //TODO 500에러 페이지 반환
        }
    }

    private HttpResponse resolveStaticFileResponse(String fileName) {
        View view = viewResolver.resolveStaticFileByName(fileName);
        HttpResponseBody body = new HttpResponseBody(view.getContent());
        return new HttpResponse(body);
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
