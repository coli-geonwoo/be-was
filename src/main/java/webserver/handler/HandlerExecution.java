package webserver.handler;

import http.request.HttpRequest;
import http.response.HttpResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import webserver.util.ClassScanUtils;
import webserver.argumentresolver.ArgumentResolver;

public class HandlerExecution {

    private static final List<ArgumentResolver> ARGUMENT_RESOLVERS;

    static {
        ClassScanUtils<ArgumentResolver> classscanUtils = new ClassScanUtils<>();
        List<ArgumentResolver> argumentResolvers = new ArrayList<>();
        argumentResolvers.addAll(classscanUtils.scan("webserver", ArgumentResolver.class));
        argumentResolvers.addAll(classscanUtils.scan("application", ArgumentResolver.class));
        ARGUMENT_RESOLVERS = argumentResolvers;
    }

    private final Object handler;
    private final Method method;

    public HandlerExecution(Method method) {
        ClassScanUtils<?> classscanUtils = new ClassScanUtils<>();
        this.method = method;
        this.handler = classscanUtils.makeHandlerInstance(method.getDeclaringClass());
    }

    public HttpResponse invoke(HttpRequest request) throws InvocationTargetException {
        try {
            Parameter[] parameters = method.getParameters();
            Object[] args = new Object[parameters.length];
            //TODO depth 개선
            for (int i = 0; i < parameters.length; i++) {
                for (ArgumentResolver argumentResolver : ARGUMENT_RESOLVERS) {
                    if (argumentResolver.canConvert(request, parameters[i])) {
                        args[i] = argumentResolver.resolve(request, parameters[i].getType());
                    }
                }
            }
            return (HttpResponse) method.invoke(handler, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can't Access Handler Method" + e);
        }
    }
}
