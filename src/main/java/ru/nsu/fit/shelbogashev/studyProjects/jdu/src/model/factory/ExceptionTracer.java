package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

/**
 * Exception storage pipe.
 */
public interface ExceptionTracer {
    void put(Throwable throwable);
}
