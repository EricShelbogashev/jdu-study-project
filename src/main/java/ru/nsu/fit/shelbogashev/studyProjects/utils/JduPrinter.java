package ru.nsu.fit.shelbogashev.studyProjects.utils;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.Node;

import java.io.OutputStream;

public interface JduPrinter {
    void print(OutputStream stream, Node printRoot);
}
