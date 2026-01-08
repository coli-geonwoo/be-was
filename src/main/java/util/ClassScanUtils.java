package util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

public class ClassScanUtils <T> {

    public List<T> scan(String packageName, Class<T> clazz) {
        Reflections reflections = new Reflections(packageName);
        return getSpecificClassTypes(reflections, reflections.getSubTypesOf(clazz))
                .stream()
                .map(streamClazz -> (T) makeHandlerInstance(streamClazz))
                .toList();
    }

    private Set<Class<? extends T>> getSpecificClassTypes(Reflections reflections, Set<Class<? extends T>> types) {
        //인터페이스나 추상클래스이면 구체클래스 재귀 탐색
        for (Class<? extends T> type : types) {
            if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
                types.remove(type);
                types.addAll(reflections.getSubTypesOf(type));
                return getSpecificClassTypes(reflections, types);
            }
        }
        return types;
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
