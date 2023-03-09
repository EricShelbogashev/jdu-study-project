package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.exception.JduTreePrinterException;

import java.io.OutputStream;

public interface JduTreePrinter {
    void printTo(OutputStream stream, NodeView root) throws JduTreePrinterException;

    default void print(NodeView root) throws JduTreePrinterException {
        printTo(System.out, root);
    }
}
