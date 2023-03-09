package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.exception.NodeViewTreePrinterException;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractNodeViewTreePrinter implements NodeViewTreePrinter {
    protected static final long DEFAULT_PATH_ALIGN = 128;
    protected final long align;

    public AbstractNodeViewTreePrinter() {
        this(DEFAULT_PATH_ALIGN);
    }

    public AbstractNodeViewTreePrinter(long align) {
        this.align = align;
    }

    @Override
    public void printTo(OutputStream stream, NodeView root) throws NodeViewTreePrinterException {
        printRecursive(stream, root);
    }

    protected void printRecursive(OutputStream stream, NodeView root) throws NodeViewTreePrinterException {
        try {
            printNode(stream, root);
        } catch (IOException e) {
            throw new NodeViewTreePrinterException(e);
        }
        if (root.children() == null) return;
        for (NodeView node : root.children().stream().sorted().toList()) {
            printRecursive(stream, node);
        }
    }

    abstract public void printNode(OutputStream stream, NodeView node) throws IOException;
}