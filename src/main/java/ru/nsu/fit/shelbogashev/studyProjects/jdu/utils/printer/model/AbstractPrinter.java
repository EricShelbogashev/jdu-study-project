package ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.model;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.model.tree.api.Node;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.utils.printer.api.JduPrinter;

import java.io.IOException;
import java.io.OutputStream;

abstract public class AbstractPrinter implements JduPrinter {
    protected static final long DEFAULT_PATH_ALIGN = 128;
    protected final long align;

    public AbstractPrinter() {
        this(DEFAULT_PATH_ALIGN);
    }

    public AbstractPrinter(long align) {
        this.align = align;
    }

    @Override
    public void printAll(OutputStream stream, Node printRoot) {
        printRecursive(stream, printRoot);
    }

     private void printRecursive(OutputStream stream, Node root) {
        try {
            print(stream, root);
        } catch (IOException ignored) {
        }
        if (root.children() == null) return;
        for (Node node : root.children().stream().sorted().toList()) {
            printRecursive(stream, node);
        }
    }

    @Override
    abstract public void print(OutputStream stream, Node printRoot) throws IOException;

}
