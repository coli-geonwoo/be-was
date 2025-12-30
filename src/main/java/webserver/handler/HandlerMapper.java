package webserver.handler;

import java.util.List;

public class HandlerMapper {

    private final List<Handler> handlers;

    public HandlerMapper() {
        this.handlers = List.of(
                new IndexHandler(),
                new RegisterHandler(),
                new DefaultViewHandler()
        );
    }

    public Handler mapByPath(String path) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(path))
                .findAny()
                .orElseThrow(() -> new RuntimeException("No handler found for path: " + path));
    }
}
