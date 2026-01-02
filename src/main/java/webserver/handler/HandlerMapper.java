package webserver.handler;

import java.util.List;
import java.util.Optional;
import org.reflections.Reflections;

public class HandlerMapper {

    private final List<Handler> handlers;

    public HandlerMapper() {
        Reflections reflections = new Reflections("application");
        this.handlers = reflections.getSubTypesOf(Handler.class)
                .stream()
                .map(this::makeHandlerInstance)
                .toList();
    }

    private Handler makeHandlerInstance(Class<? extends Handler> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception exception) {
            throw new RuntimeException("Can't instantiate " + clazz.getName(), exception);
        }
    }

    public Optional<Handler> mapByPath(String path) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(path))
                .findAny();
    }
}
