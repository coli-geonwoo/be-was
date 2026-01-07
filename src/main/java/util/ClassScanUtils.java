package util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

public class ClassScanUtils {

    public List<Object> scan(String packageName, Class<?> clazz) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(clazz)
                .stream()
                .map(this::makeHandlerInstance)
                .toList();
    }

    public Object makeHandlerInstance(Class<?> clazz) {
        try {
            Constructor<?> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            Object instance = declaredConstructor.newInstance();
            declaredConstructor.setAccessible(false);
            return instance;
        } catch (Exception exception) {
            throw new RuntimeException("Can't instantiate " + clazz.getName(), exception);
        }
    }

    public List<Class<?>> scanAnnotatedClasses(String packageName, Class<? extends Annotation> annotation) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackages(packageName)
                        .addScanners(Scanners.TypesAnnotated)
        );
        return reflections.getTypesAnnotatedWith(annotation)
                .stream()
                .toList();
    }

    public Set<Method> scanAnnotatedMethods(
            String packageName,
            Class<? extends Annotation> annotationClass
    ) {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackages(packageName)
                        .addScanners(Scanners.MethodsAnnotated)
        );
        return reflections.getMethodsAnnotatedWith(annotationClass);
    }


}
