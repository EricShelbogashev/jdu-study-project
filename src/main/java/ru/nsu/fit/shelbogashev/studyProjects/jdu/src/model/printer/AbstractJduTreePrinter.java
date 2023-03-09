package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.exception.JduTreePrinterException;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractJduTreePrinter extends JduTreePrinter {
    protected static final long DEFAULT_PATH_ALIGN = 128;
    protected final long align;

    public AbstractJduTreePrinter() {
        this(DEFAULT_PATH_ALIGN);
    }

    public AbstractJduTreePrinter(long align) {
        this.align = align;
    }

    @Override
    void printTo(OutputStream stream, NodeView root) throws JduTreePrinterException {
        printRecursive(stream, root);
    }

    private void printRecursive(OutputStream stream, NodeView root) throws JduTreePrinterException {
        try {
            printNode(stream, root);
        } catch (IOException e) {
            throw new JduTreePrinterException(e);
        }
        if (root.children() == null) return;
        for (NodeView node : root.children().stream().sorted().toList()) {
            printRecursive(stream, node);
        }
    }

    abstract public void printNode(OutputStream stream, NodeView node) throws IOException;
}