package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {
    int order() default Integer.MAX_VALUE;
}
