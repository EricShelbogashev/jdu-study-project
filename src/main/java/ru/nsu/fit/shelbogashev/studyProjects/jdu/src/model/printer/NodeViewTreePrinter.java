package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.exception.NodeViewTreePrinterException;

import java.io.OutputStream;

public interface NodeViewTreePrinter {
    void printTo(OutputStream stream, NodeView root, NodeViewTreePrinterOptions options) throws NodeViewTreePrinterException;

    @SuppressWarnings("unused")
    default void print(NodeView root, NodeViewTreePrinterOptions options) throws NodeViewTreePrinterException {
        printTo(System.out, root, options);
    }
}
