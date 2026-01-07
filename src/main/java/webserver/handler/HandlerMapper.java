package webserver.handler;

import http.HttpMethod;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.ClassScanUtils;

public class HandlerMapper {

    private static final HandlerMapper APPLICATION_HANDLER_MAPPER;

    static {
        ClassScanUtils classScanUtils = new ClassScanUtils();
        Map<HandlerKey, Method> map = new HashMap<>();
        List<Method> handlerMethod = classScanUtils.scanAnnotatedClasses("application", HttpHandler.class)
                .stream()
                .map(Class::getMethods)
                .flatMap(Arrays::stream)
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .toList();
        for (Method method : handlerMethod) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            map.put(new HandlerKey(requestMapping.method(), requestMapping.path()), method);
        }
        APPLICATION_HANDLER_MAPPER = new HandlerMapper(map);
    }

    private List<Handler> handlers;
    private Map<HandlerKey, Method> handlerMethods;

    public static HandlerMapper getInstance() {
        return APPLICATION_HANDLER_MAPPER;
    }

    private HandlerMapper(List<Handler> handlers) {
        this.handlers = handlers;
    }

    private HandlerMapper(Map<HandlerKey, Method> handlerMethods) {
        this.handlerMethods = handlerMethods;
    }

    public Handler mapByPath(String path) {
        return handlers.stream()
                .filter(handler -> handler.canHandle(path))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Not Matched Handler: " + path));
    }

    public Method mapByPath(HttpMethod method, String path) {
        return handlerMethods.entrySet().stream()
                .filter(entry -> entry.getKey().matches(method, path))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Not Matched Handler: " + path))
                .getValue();
    }
}
