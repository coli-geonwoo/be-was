package webserver.handler;

import java.util.List;

public class HandlerMapper {

    private final List<Handler> handlers;

    public HandlerMapper(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public Handler mapByPath(String path) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(path))
                .findAny()
                .orElseThrow(() -> new RuntimeException("No handler found for path: " + path));
    }
}
