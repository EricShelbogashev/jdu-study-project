package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.exception.NodeViewTreePrinterException;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractNodeViewTreePrinter implements NodeViewTreePrinter {
    int limit;
    int depth;
    boolean symbolicLinkFollow;

    /**
     * Prints the tree to the stream using special formatting.
     *
     * @param stream where will the tree be displayed.
     * @param root   tree root.
     * @throws NodeViewTreePrinterException if stream is invalid.
     */
    @Override
    public void printTo(OutputStream stream, NodeView root, NodeViewTreePrinterOptions options) throws NodeViewTreePrinterException {
        this.limit = options.limit();
        this.depth = options.depth();
        this.symbolicLinkFollow = options.symbolicLinkFollow();
        printRecursive(stream, root, -1);
    }

    protected void printRecursive(OutputStream stream, NodeView root, int currentDepth) throws NodeViewTreePrinterException {
        if (currentDepth == depth) return;
        try {
            printNode(stream, root);
        } catch (IOException e) {
            throw new NodeViewTreePrinterException(e);
        }
        if (root.children() == null) return;
        root.children().stream()
                .sorted()
                .limit(limit)
                .forEach(node -> printRecursive(stream, node, currentDepth + 1));
    }

    /**
     * Outputs a single node representation to the stream.
     *
     * @param stream will take a String representation of the node.
     * @param node   node for printing.
     * @throws IOException if stream is invalid.
     */
    abstract public void printNode(OutputStream stream, NodeView node) throws IOException;
}