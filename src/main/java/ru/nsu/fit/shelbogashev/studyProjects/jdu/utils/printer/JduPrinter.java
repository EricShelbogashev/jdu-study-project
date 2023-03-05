package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;

import java.io.OutputStream;

public interface JduPrinter {
    void print(OutputStream stream, Node printRoot, long depth, Limit limit);
}
