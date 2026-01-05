package webserver.handler;

import java.util.List;
import java.util.Optional;
import util.ClassScanUtils;

public class HandlerMapper {

    private static final ClassScanUtils<Handler> APPLICATION_HANDLER_SCANNER = new ClassScanUtils<>();

    private final List<Handler> handlers;

    public static HandlerMapper fromApplicationHandlers() {
        List<Handler> handlers = APPLICATION_HANDLER_SCANNER.scan("application", Handler.class);
        return new HandlerMapper(handlers);
    }

    public HandlerMapper(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public Optional<Handler> mapByPath(String path) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(path))
                .findAny();
    }
}
