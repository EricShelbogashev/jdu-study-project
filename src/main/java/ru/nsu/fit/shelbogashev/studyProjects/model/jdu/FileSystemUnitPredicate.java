package ru.nsu.fit.shelbogashev.studyProjects.model.jdu;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class FileSystemUnitPredicate implements Predicate<Class<?>> {

    @Override
    public boolean test(Class<?> aClass) {
        if (!Arrays.asList(aClass.getInterfaces()).contains(Node.class)) return false;
        Annotation[] annotations = aClass.getAnnotations();
        return Arrays.stream(annotations).anyMatch(it -> it.annotationType() == FileSystemUnit.class);
    }
}
