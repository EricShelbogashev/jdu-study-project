package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.factory;

import java.util.ArrayList;

public class ExceptionTracerImpl implements ExceptionTracer {
    private final ArrayList<Throwable> list;

    public ExceptionTracerImpl() {
        this.list = new ArrayList<>();
    }

    @Override
    public void put(Throwable throwable) {

    }

    public ArrayList<Throwable> getList() {
        return list;
    }
}
