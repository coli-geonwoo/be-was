package util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;

public class ClassScanUtils<T> {

    public List<T> scan(String packageName, Class<? extends T> clazz) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(clazz)
                .stream()
                .map(this::makeHandlerInstance)
                .toList();
    }

    public Set<Method> scanAnnotatedMethods(
            String packageName,
            Class<? extends Annotation> annotationClass
    ) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getMethodsAnnotatedWith(annotationClass);
    }

    private T makeHandlerInstance(Class<? extends T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception exception) {
            throw new RuntimeException("Can't instantiate " + clazz.getName(), exception);
        }
    }
}
