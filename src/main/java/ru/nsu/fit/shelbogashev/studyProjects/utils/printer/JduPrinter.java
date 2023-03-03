package ru.nsu.fit.shelbogashev.studyProjects.utils.printer;

import ru.nsu.fit.shelbogashev.studyProjects.model.jdu.tree.Node;

import java.io.OutputStream;

public interface JduPrinter {
    void print(OutputStream stream, Node printRoot, long depth);
}
