package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.api;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;

import java.io.IOException;
import java.io.OutputStream;

public interface JduPrinter {
    void printAll(OutputStream stream, Node printRoot);
    void print(OutputStream stream, Node printRoot) throws IOException;
}
