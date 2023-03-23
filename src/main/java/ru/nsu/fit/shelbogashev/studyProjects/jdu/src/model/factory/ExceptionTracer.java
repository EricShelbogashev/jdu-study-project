package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

/**
 * Exception storage pipe.
 */
// CR: merge with implementation
public interface ExceptionTracer {
    void put(Throwable throwable);
}
