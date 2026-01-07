package webserver.exception;

import http.response.HttpResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import util.ClassScanUtils;

public class ExceptionHandlerRegistry {

    private final Map<Class<? extends Exception>, Method> exceptionHandlers;

    public static final ExceptionHandlerRegistry fromApplication() {
        ClassScanUtils classScanUtils = new ClassScanUtils();
        Set<Method> exceptionHandlers = classScanUtils.scanAnnotatedMethods("application.exception", ExceptionHandler.class);

        Map<Class<? extends Exception>, Method> mappedExceptionHandlers = exceptionHandlers.stream()
                .collect(Collectors.toMap(
                        method -> method.getAnnotation(ExceptionHandler.class).value(),
                        method -> method)
                );
        return new ExceptionHandlerRegistry(mappedExceptionHandlers);
    }

    public ExceptionHandlerRegistry(Map<Class<? extends Exception>, Method> exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
    }

    public HttpResponse handleByExceptionHandler(Exception exception) {
        Class<? extends Exception> exceptionClass = exception.getClass();
        Method method = exceptionHandlers.get(exceptionClass);
        try {
            Object instance = method.getDeclaringClass()
                    .getConstructor()
                    .newInstance();
            return (HttpResponse) method.invoke(instance, exception);
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred while handling exception", e);
        }
    }
}
