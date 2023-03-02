package ru.nsu.fit.shelbogashev.studyProjects.model.jdu;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.function.Predicate;

public class FileSystemUnitPredicate implements Predicate<Class<?>> {

    @Override
    public boolean test(Class<?> aClass) {
        if (!Node.class.isAssignableFrom(aClass)) return false;
        Annotation[] annotations = aClass.getAnnotations();
        if (Arrays.stream(annotations).noneMatch(it -> it.annotationType() == NodeHandler.class)) return false;
        // TODO: проверить, какие аннотации выводятся.
        //  Если аннотации методов не выводятся, вывести их и сделать проверку на NodeHandlerFabricMethod.
        return true;
    }
}
