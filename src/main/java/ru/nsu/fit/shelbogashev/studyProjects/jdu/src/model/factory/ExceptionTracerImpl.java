package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import java.util.ArrayList;
import java.util.List;

public class ExceptionTracerImpl implements ExceptionTracer {
    private final List<Throwable> list;

    public ExceptionTracerImpl() {
        this.list = new ArrayList<>();
    }

    @Override
    public void put(Throwable throwable) {
        list.add(throwable);
    }

    @SuppressWarnings("unused")
    public List<Throwable> getList() {
        return list;
    }
}
