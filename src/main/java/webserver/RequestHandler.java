package webserver;

import http.response.HttpResponse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
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
    private final ViewResolver viewResolver;
    private final HttpRequestParserFacade requestParserFacade;
    private final HttpResponseResolveFacade responseResolveFacade;
    private final HandlerMapper handlerMapper;

    public RequestHandler(
            Socket connection,
            ViewResolver viewResolver,
            HttpRequestParserFacade requestParserFacade,
            HttpResponseResolveFacade responseResolveFacade,
            HandlerMapper handlerMapper
    ) {
        this.connection = connection;
        this.viewResolver = viewResolver;
        this.requestParserFacade = requestParserFacade;
        this.responseResolveFacade = responseResolveFacade;
        this.handlerMapper = handlerMapper;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
             OutputStream out = connection.getOutputStream()) {
            String rawRequest = getRawHttpRequest(br);

            if(!rawRequest.isBlank()) {
                HttpRequest request = requestParserFacade.parse(rawRequest);
                Handler handler = handlerMapper.mapByPath(request.getRequestUrl());
                HttpResponse response = handler.handle(request);
                responseResolveFacade.resolve(request, response, new DataOutputStream(out));

//                DataOutputStream dos = new DataOutputStream(out);
//                byte[] body = resolvedView.getContent();
//                response200Header(ContentType.mapToType(requestUrl), dos, body.length);
//                responseBody(dos, body);
                logger.debug("New Client Connect Response: {}", request.getRequestUrl());
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
            //TODO 500에러 페이지 반환
        }
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

    private void response200Header(String contentType, DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + contentType + "\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
