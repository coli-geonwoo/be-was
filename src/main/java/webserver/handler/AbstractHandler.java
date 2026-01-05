package webserver.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;

public abstract class AbstractHandler implements Handler {

    @Override
    public abstract boolean canHandle(String path);

    @Override
    public final HttpResponse handle(HttpRequest request) {
        if(request.isGet()) {
            return doGet(request);
        }

        if(request.isPost()) {
            return doPost(request);
        }
        throw new UnsupportedOperationException("지원되지 않는 http method 입니다");
    }

    protected HttpResponse doGet(HttpRequest request) {
        throw new UnsupportedOperationException("지원되지 않는 메서드입니다");
    }

    protected HttpResponse doPost(HttpRequest request) {
        throw new UnsupportedOperationException("지원되지 않는 메서드입니다");
    }
}
