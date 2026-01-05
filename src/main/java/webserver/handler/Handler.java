package webserver.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;

public interface Handler {

    boolean canHandle(String path);

    HttpResponse handle(HttpRequest request);
}
