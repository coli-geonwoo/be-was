package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.exception.ExceptionHandlerRegistry;
import webserver.handler.HandlerMapper;
import webserver.handler.ViewHandler;
import webserver.parse.request.HttpRequestParserFacade;
import webserver.resolver.HttpResponseResolveFacade;
import webserver.view.ViewResolver;

public class WebServer {

    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(
            10,
            200,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadPoolExecutor.CallerRunsPolicy()

    ); //기본 tomcat 설정
    private static final Logger logger = LoggerFactory.getLogger(WebServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final HttpRequestParserFacade HTTP_REQUEST_PARSER = new HttpRequestParserFacade();

    public static void main(String args[]) throws Exception {
        int port = 0;
        if (args == null || args.length == 0) {
            port = DEFAULT_PORT;
        } else {
            port = Integer.parseInt(args[0]);
        }

        // 서버소켓을 생성한다. 웹서버는 기본적으로 8080번 포트를 사용한다.
        try (ServerSocket listenSocket = new ServerSocket(port)) {
            logger.info("Web Application Server started {} port.", port);

            // 클라이언트가 연결될때까지 대기한다.
            Socket connection;
            while ((connection = listenSocket.accept()) != null) {
                RequestHandler requestHandler = new RequestHandler(
                        connection,
                        HTTP_REQUEST_PARSER,
                        new HttpResponseResolveFacade(),
                        HandlerMapper.getInstance(),
                        new ViewHandler(new ViewResolver()),
                        ExceptionHandlerRegistry.fromApplication()
                );
                EXECUTOR.submit(requestHandler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
