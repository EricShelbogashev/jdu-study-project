package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception storage pipe.
 */
public class ExceptionTracer {
    private final List<Throwable> list;

    public ExceptionTracer() {
        this.list = new ArrayList<>();
    }

    public void put(Throwable throwable) {
        list.add(throwable);
    }

    @SuppressWarnings("unused")
    public List<Throwable> getList() {
        return list;
    }
}
