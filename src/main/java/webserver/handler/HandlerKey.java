package webserver.handler;

import http.HttpMethod;
import java.util.Arrays;

public class HandlerKey {

    private final HttpMethod method;
    private final String[] paths;

    public HandlerKey(HttpMethod method, String[] paths) {
        this.method = method;
        this.paths = paths;
    }

    public boolean matches(HttpMethod method, String path) {
        return method == this.method && Arrays.asList(paths).contains(path);
    }
}
