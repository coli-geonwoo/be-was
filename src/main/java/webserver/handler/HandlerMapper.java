package webserver.handler;

import java.util.List;
import java.util.Optional;
import util.ClassScanUtils;

public class HandlerMapper {

    private static final HandlerMapper APPLICATION_HANDLER_MAPPER;

    static {
        ClassScanUtils<Handler> classScanUtils = new ClassScanUtils<>();
        List<Handler> handlers = classScanUtils.scan("application", AbstractHandler.class);
        APPLICATION_HANDLER_MAPPER = new HandlerMapper(handlers);
    }

    private final List<Handler> handlers;

    public static HandlerMapper getInstance() {
        return APPLICATION_HANDLER_MAPPER;
    }

    private HandlerMapper(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public Handler mapByPath(String path) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(path))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Not Matched Handler: " +  path));
    }
}
