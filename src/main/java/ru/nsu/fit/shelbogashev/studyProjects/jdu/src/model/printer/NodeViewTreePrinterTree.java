package ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer;

import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.formatter.PrinterFormatter;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.AtomicType;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.node.NodeView;
import ru.nsu.fit.shelbogashev.studyProjects.jdu.src.model.printer.exception.NodeViewTreePrinterException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class NodeViewTreePrinterTree extends AbstractNodeViewTreePrinter {
    private final PrinterFormatter printerFormatter;
    private int offset;

    public NodeViewTreePrinterTree(PrinterFormatter formatter) {
        this.printerFormatter = formatter;
        this.offset = 0;
    }

    @Override
    public void printTo(OutputStream stream,
                        NodeView root,
                        NodeViewTreePrinterOptions options
    ) throws NodeViewTreePrinterException {
        offset = root.path().getNameCount();
        super.printTo(stream, root, options);
    }

    @Override
    public void printNode(OutputStream stream, NodeView node) throws IOException {
        String filename = String.valueOf(node.path().getFileName());
        if (node.atomicType() == AtomicType.SYMBOLIC_LINK) {
            filename += " -> " + Files.readSymbolicLink(node.path());
        }
        String output = String.format(
                " ".repeat(
                        this.printerFormatter.treeFormatter().childAlign() * (node.path().getNameCount() - offset)
                )
                        + "%s%s"
                        + " ".repeat(this.printerFormatter.treeFormatter().childAlign()) + "[%s]\n",
                node.atomicType() == AtomicType.DIRECTORY ? "/" : "",
                filename,
                node.size().getString(this.printerFormatter.sizeFormatter())
        );

        stream.write(output.getBytes());
    }
}
