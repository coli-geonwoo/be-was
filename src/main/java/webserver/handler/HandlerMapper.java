package webserver.handler;

import application.handler.IndexHandler;
import application.handler.RegisterHandler;
import application.handler.UserCreateHandler;
import java.util.List;

public class HandlerMapper {

    private final List<Handler> handlers;

    public HandlerMapper() {
        this.handlers = List.of(
                new IndexHandler(),
                new RegisterHandler(),
                new UserCreateHandler(),
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
