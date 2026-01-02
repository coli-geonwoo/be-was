package util;

import java.util.List;
import org.reflections.Reflections;

public class ClassScanUtils<T> {

    public List<T> scan(String packageName, Class<? extends T> clazz) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(clazz)
                .stream()
                .map(this::makeHandlerInstance)
                .toList();
    }

    private T makeHandlerInstance(Class<? extends T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception exception) {
            throw new RuntimeException("Can't instantiate " + clazz.getName(), exception);
        }
    }
}
